package com.example.sandbox;


import com.example.sandbox.foo.FooImpl;
import com.example.sandbox.foo.FooInterface;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

class ForReadingTest {

	@Test
	void readingAnnotationConfigApplicationContext() {
		try (AnnotationConfigApplicationContext context
					 = new AnnotationConfigApplicationContext()) {

			context.register(FooImpl.class);
			context.refresh();
			final FooInterface bean = context.getBean(FooInterface.class);

			assertThat(bean).isInstanceOf(FooInterface.class);

		}
	}

}
