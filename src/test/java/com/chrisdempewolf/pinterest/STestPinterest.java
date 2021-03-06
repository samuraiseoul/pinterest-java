package com.chrisdempewolf.pinterest;

import com.chrisdempewolf.pinterest.fields.board.BoardFields;
import com.chrisdempewolf.pinterest.fields.pin.PinFields;
import com.chrisdempewolf.pinterest.responses.board.Board;
import com.chrisdempewolf.pinterest.responses.board.BoardResponse;
import com.chrisdempewolf.pinterest.responses.board.Boards;
import com.chrisdempewolf.pinterest.responses.pin.Pin;
import com.chrisdempewolf.pinterest.responses.pin.PinPage;
import com.chrisdempewolf.pinterest.responses.pin.PinResponse;
import com.chrisdempewolf.pinterest.responses.pin.Pins;
import com.google.gson.Gson;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class STestPinterest {
    private static final String PIN_ID = "422705115002975322";
    private static final String PIN_WITH_ATTRIBUTION = "12877548912849493";
    private static final String BOARD_NAME = "francisabila/all-about-me";

    private final Pinterest pinterest = new Pinterest(loadAccessToken());

    @Test
    public void testWithAttribution() throws IOException {
        final PinResponse actualPinResponse = pinterest.getPin(PIN_WITH_ATTRIBUTION, new PinFields().setAll());
        final PinResponse expectedPinResponse = new Gson().fromJson(loadFile("com/chrisdempewolf/PinWithAttribution.json"), PinResponse.class);
        assertEquals(expectedPinResponse, actualPinResponse);
    }

    @Test
    public void testPinWithAllFields() throws IOException {
        final PinResponse actualPinResponse = pinterest.getPin(PIN_ID, new PinFields().setAll());
        final PinResponse expectedPinResponse = new Gson().fromJson(loadFile("com/chrisdempewolf/CompletePinResponse.json"), PinResponse.class);
        assertEquals(expectedPinResponse, actualPinResponse);
    }

    @Test
    public void testPinWithDefaultFields() throws IOException {
        final PinResponse actualPinResponse = pinterest.getPin(PIN_ID);
        final PinResponse expectedPinResponse = new Gson().fromJson(loadFile("com/chrisdempewolf/DefaultPinResponse.json"), PinResponse.class);
        assertEquals(expectedPinResponse, actualPinResponse);
    }

    @Test
    public void testBoardPinsWithDefaultFields() throws IOException {
        final Pins pins = pinterest.getPinsFromBoard(BOARD_NAME);

        assertNotNull(pins.getNextPage());
        assertNotNull(pins.getPins());

        for (final Pin pin : pins) {
            assertNotNull(pin.toString(), pin.getId());
            assertNotNull(pin.toString(), pin.getNote());
            assertNotNull(pin.toString(), pin.getUrl());
            assertNotNull(pin.toString(), pin.getLink());
            assertNull(pin.toString(), pin.getCounts());
            assertNull(pin.toString(), pin.getBoard());
            assertNull(pin.toString(), pin.getColor());
            assertNull(pin.toString(), pin.getCreatedAt());
            assertNull(pin.toString(), pin.getCreator());
            assertNull(pin.toString(), pin.getAttribution());
            assertNull(pin.toString(), pin.getMetaData());
        }
    }

    @Test
    public void testBoardPinsWithAllFields() throws IOException {
        final Pins pins = pinterest.getPinsFromBoard(BOARD_NAME, new PinFields().setAll());

        assertNotNull(pins.getNextPage());
        assertNotNull(pins.getPins());

        for (final Pin pin : pins) {
            assertNotNull(pin.toString(), pin.getId());
            assertNotNull(pin.toString(), pin.getNote());
            assertNotNull(pin.toString(), pin.getUrl());
            assertNotNull(pin.toString(), pin.getLink());
            assertNotNull(pin.toString(), pin.getCounts());
            assertNotNull(pin.toString(), pin.getBoard());
            assertNotNull(pin.toString(), pin.getColor());
            assertNotNull(pin.toString(), pin.getCreatedAt());
            assertNotNull(pin.toString(), pin.getCreator());
            assertNull(pin.toString(),    pin.getAttribution()); //attribution is null for this Pin
            assertNotNull(pin.toString(), pin.getMetaData());
        }
    }

    @Test
    public void testGetNextPageOfPins() {
        final PinPage page = pinterest.getPinsFromBoard(BOARD_NAME).getNextPage();
        final Pins pins = pinterest.getNextPageOfPins(page);
        assertNotNull(pins);
    }

    @Test
    public void testBoardWithDefaultFields() throws IOException {
        final BoardResponse boardResponse = pinterest.getBoard(BOARD_NAME);
        final Board board = boardResponse.getBoard();

        assertNull(board.getCounts());
        assertNull(board.getCreatedAt());
        assertNull(board.getDescription());
        assertNotNull(board.getId());
        assertNull(board.getImage());
        assertNotNull(board.getName());
        assertNotNull(board.getUrl());
    }

    @Test
    public void testBoardWithAllFields() throws IOException {
        final BoardResponse boardResponse = pinterest.getBoard(BOARD_NAME, new BoardFields().setAll());
        final Board board = boardResponse.getBoard();

        assertNotNull(board.getCounts());
        assertNotNull(board.getCreatedAt());
        assertNotNull(board.getDescription());
        assertNotNull(board.getId());
        assertNotNull(board.getImage());
        assertNotNull(board.getName());
        assertNotNull(board.getUrl());
    }

    @Test
    public void testMyBoardWithDefaultFields() throws IOException {
        final Boards boards = pinterest.getMyBoards();
        final List<Board> boardList = boards.getBoards();

        for (final Board board : boardList) {
            assertNull(board.getCounts());
            assertNull(board.getCreatedAt());
            assertNull(board.getDescription());
            assertNotNull(board.getId());
            assertNull(board.getImage());
            assertNotNull(board.getName());
            assertNotNull(board.getUrl());
        }
    }

    @Test
    public void testMyBoardWithAllFields() throws IOException {
        final Boards boards = pinterest.getMyBoards(new BoardFields().setAll());
        final List<Board> boardList = boards.getBoards();

        for (final Board board : boardList) {
            assertNotNull(board.getCounts());
            assertNotNull(board.getCreatedAt());
            assertNotNull(board.getDescription());
            assertNotNull(board.getId());
            assertNotNull(board.getImage());
            assertNotNull(board.getName());
            assertNotNull(board.getUrl());
        }
    }

    private static String loadFile(final String resource) throws IOException {
        return IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream(resource));
    }

    private static String loadAccessToken() {
        try {
            return FileUtils.readFileToString(FileUtils.getFile(".access_token"), Charsets.UTF_8).replace("\n", "");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
