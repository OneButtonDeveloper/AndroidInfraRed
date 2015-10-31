# Android Infra Red

Open source project for transmitting signals via IR-blasters on Android devices

Created in Android Studio

Supports Samsung, HTC, some LG devices with IR

Contains:
- SampleApplication
- InfraRed library

How to use, see: [MainActivity.java](https://github.com/OneButtonDeveloper/AndroidInfraRed/blob/master/app/src/main/java/com/obd/infrared/sample/MainActivity.java)

Known issues: One of the project files contains broken characters. Just rename it again.

Email: one.button.developer@gmail.com


## Menu

- **[Basis](#basis)**
  - [Basic theory](#basic-theory)
  - [Sequence types](#sequence-types)
  - [Converting the patterns](#converting-the-patterns)
- **How to use**
- **Details**

## Basis


### Basic theory

In order to remotely control a device via IR, you require a transmitter (mobile phone with IR blaster) and a control signal sequence that you want to send. This sequence is the key to the function that the device is supposed to perform (e.g. the function of a TV to turns itself on). The built-in IR receiver of the controlled device analyzes all incoming signals. When they match a pattern of a particular function, the devices calls that function.

The control signal sequence consists of two parameters: The frequency (in _Hertz_) and the pattern, which is a time sequence of _ON_/_OFF_ signals on the specified carrier frequency. Such patterns can be found on the Internet.

### Sequence types

The pattern is pictured by a sequence of numbers either of the hexadecimal (_HEX_) or the decimal (_DEC_) numeral system.

Here's an example:

```
Frequency:
  33000 Hertz (33KHz)
Patterns:
  0) 0x01f4 0x1c84 0x01f4 0x00c8 or (01f4 1c84 01f4 00c8)
  1) 500 7300 500 200
  2) 17 241 17 7
```

The main difference between the patterns in this example is their notation.
Each number in the pattern describes the duration of a single _ON_/_OFF_ signal, only that patterns 0 and 1 describe it in microseconds, while patterns 2 describes it in cycles.

One cycle always consists of an an _ON_ and an _OFF_ signal (the image below shows cycles with a frequency of 40000 _Hz_).

![Difference between sequences](http://s8.postimg.org/s9s4dxumd/91598ad1_1766_469e_8731_99f70f606864.jpg)

### Converting the patterns

The length of a single period _T_ in microseconds equals 1 000 000 (1 second in microseconds) divided by the frequency _f_ (in _Hertz_) 

> _T_ = 1 000 000 _ms_ / _f_

For example

> _T_ = 1 000 000 _ms_/ 33000 _Hz_ ≈ 30.3 _ms_

To covert a microsecond pattern to a cycle pattern, divide each value by _T_

To convert a cycle pattern to a microsecond pattern, simply multiply each value by _T_
