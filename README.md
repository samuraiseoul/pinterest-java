# Description

Public Java SDK for [Pinterest's new API](https://developers.pinterest.com/docs/getting-started/introduction/).

# Maven

```xml
<dependency>
    <groupId>com.chrisdempewolf</groupId>
    <artifactId>pinterest-sdk</artifactId>
    <version>1.9</version>
</dependency>
```

# Examples

### Initialization

- Construct a new Pinterest SDK
```java 
 final Pinterest pinterest = new Pinterest("<INSERT_YOUR_PINTEREST_ACCESS_TOKEN>");
```
    
### Pin Methods

#### *Fetching*
    
- To get a Pin (with **all fields**) via a Pin ID:
  - Example Pin ID:  `525091637782793357` from URL: `https://www.pinterest.com/pin/525091637782793357/`
```java 
final PinResponse pin = pinterest.getPin("<INSERT_PIN_ID>", new PinFields().setAll());
```
   
- To get a Pin with only **default fields** (URL, note, link, ID) set
```java
final PinResponse pin = pinterest.getPin("<INSERT_PIN_ID>");
```
  
- To get a Pin with only **link, created_at, and color** set
```java
final PinResponse pin = pinterest.getPin("<INSERT_PIN_ID>", new PinFields().setLink().setCreatedAt().setColor());
```
  
- To get **your own** Pins (with **all fields**):
```java 
final Pins pins = pinterest.getMyPins(new PinFields().setAll());
pins.forEach(pin -> {System.out.println(pin);});
```
    
- To get all the Pins **from a board** with default fields
  - Example board name:  `cdatarank/欲しいもの/` from URL:  `https://www.pinterest.com/cdatarank/欲しいもの/`
```java 
final Pins pins = pinterest.getPinsFromBoard("<INSERT_BOARD_NAME>");
```
    
- To get all the Pins **from a board** with **all fields**
```java 
final Pins pins = pinterest.getPinsFromBoard("<INSERT_BOARD_NAME>", new PinFields().setAll());
```
  
- **Paging** through Pin responses
```java
Pins pins = pinterest.getPinsFromBoard(BOARD_NAME);
while (pins.getNextPage() != null) {
    pins = pinterest.getNextPageOfPins(pins.getNextPage());
}
```

#### *Deleting*

All you need is the Pin ID and an access token with write access to the Pin question.
This method returns `true` if the Pin was successfully deleted; `false` otherwise.

```java
final boolean deleted = pinterest.deletePin("<INSERT_PIN_ID>");
```
  
### Board Methods

#### *Fetching*

-  To get info about a particular Board with **default** fields:
  - Example Board name: `cdatarank/欲しいもの`
    from URL:  `https://www.pinterest.com/cdatarank/欲しいもの/`
```java
final BoardResponse boardResponse = pinterest.getBoard("<INSERT_BOARD_NAME>");
final Board board = boardResponse.getBoard();
```

-  To get info about a particular Board with **all** fields:
```java
final BoardResponse boardResponse = pinterest.getBoard("<INSERT_BOARD_NAME>", new BoardFields().setAll());
final Board board = boardResponse.getBoard();
```
  
... more to come soon. (non-GET endpoints are currently in construction)

# Contributing

Found a bug? Have a suggestion? Feel free to send me PR or make an issue on the repo!

### Tests

To run the integration tests, put your Pinterest access token in the root of this directory in a file called `.access_token`.
