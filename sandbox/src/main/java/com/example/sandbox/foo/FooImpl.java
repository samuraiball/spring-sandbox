package com.example.sandbox.foo;

public class FooImpl implements FooInterface {
	@Override
	public void println(String s) {
		System.out.println("s = " + s);
	}
}
