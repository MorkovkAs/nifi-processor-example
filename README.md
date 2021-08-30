# NiFi Custom Processor Example

It is a simple HelloWorld example of a NiFi Processor.\
It prints `Hello ` + a given name to FlowFile Attribute and Content.

## Installation

Clone the source locally:
```
$ git clone https://github.com/MorkovkAs/nifi-processor-example/
```
Clean and install project with maven:
```
$ cd nifi-processor-example/
$ mvn clean install
```

## Processor Input Params

#### name

*Required*\
*Supports NiFi Expression Language*\
Type: `String`

The name to print with `Hello`.

## Thanks!
Any questions or problems give me a shout on email avklimakov@gmail.com

## License
Copyright 2021 Anton Klimakov\
Licensed under the Apache License, Version 2.0