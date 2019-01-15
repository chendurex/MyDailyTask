package com.jdk.optional;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author cheny.huang
 * @date 2018-12-29 11:02.
 */
public class BasicUseage {
    /**
     * 创建一个空的optional，执行get操作时，将提示错误
     */
    @Test(expected = NoSuchElementException.class)
    public void createEmptyOptionalThenNull() {
        Optional<User> emptyOpt = Optional.empty();
        emptyOpt.get();
    }

    /**
     * 不能通过of设置值，of设置的值是必须存在的
     */
    @Test(expected = NullPointerException.class)
    public void whenCreateOfEmptyOptionalThenNPE() {
        Optional<User> opt = Optional.of(null);
    }

    /**
     * 如果实在需要设置值，则修改为ofNullable
     */
    @Test
    public void createOfEmptyOptional() {
        Optional.ofNullable(null);
    }

    @Test
    public void accessOfNullableOptional() {
        String name = "hello";
        Optional<String> opt = Optional.of(name);
        assertEquals(name, opt.get());
    }

    /**
     * 为了防止npe，在获取值时候先检查是否存在值
     */
    @Test
    public void checkIfPresentThenOk() {
        User user = new User("hello");
        Optional<User> opt = Optional.ofNullable(user);
        assertTrue(opt.isPresent());
        assertEquals(user.getMail(), opt.get().getMail());
        // 如果存在则执行函数
        opt.ifPresent(s->assertEquals(s.getMail(), user.getMail()));
    }

    /**
     * 如果存在值则返回第一个，否则返回默认值，这个非常适用于判断空然后返回默认值场景
     */
    @Test
    public void whenEmptyValueThenReturnDefault() {
        User user = null;
        User user2 = new User("word");
        User result = Optional.ofNullable(user).orElse(user2);
        assertEquals(user2.getMail(), result.getMail());

        User user3 = new User("hello");
        User result2 = Optional.ofNullable(user3).orElse(user2);
        assertEquals(user3.getMail(), result2.getMail());

        // 或者直接执行计算获取值，跟Map.computeIfAbsent类似
        // 注意：最好是采用这种方式获取备份数据，因为上一种orElse方法，不管对象是否为空都会计算备用值，而下面这种不会
        User result3 = Optional.ofNullable(user).orElseGet(()->new User("helloword"));
        assertEquals("helloword", result3.getMail());
    }

    /**
     * Optional还定义了 ElseThrow() API，其作用是在对象为空时，直接抛出一个异常，而不是返回一个替代值
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenThrowExceptionThenOk() {
        Optional.ofNullable(null).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Map() 将 Function 参数作为值，然后返回 Optional 中经过封装的结果。这将使我们可以在后续附加一些操作，比如此处的 orElse()
     */
    @Test
    public void whenMapThenOk() {
        User user = new User("hello");
        String v = Optional.ofNullable(user).map(User::getMail).orElse("world");
        assertEquals(user.getMail(), v);
    }

    /**
     * flatMap() 也是将 Function 参数作为 Optional 值，但它后面是直接返回结果
     */
    @Test
    public void whenFlatMapThenOk() {
        User user = new User("hello");
        String v = Optional.ofNullable(user).flatMap(s->Optional.of(s.getMail())).orElse("default");
        assertEquals(user.getMail(), v);
    }

    /**
     * Optional 类还提供了根据条件对值进行“过滤”的功能
     * filter() 方法将 predicate 作为参数，当测试评估为真时，返回实际值。否则，当测试为假时，返回值则为空 Optional。
     * 我们来看一个例子——基于非常基本的电子邮件验证，接受或者拒绝 User
     */
    @Test
    public void whenFilterThenOk() {
        User user = new User("@hello");
        Optional<User> result = Optional.of(user)
                .filter(s->Optional.ofNullable(s.getMail()).isPresent() && s.getMail().contains("@"));
        assertTrue(result.isPresent());
        assertEquals(result.get().getMail(), user.getMail());
    }

    /**
     * 用这种链式的判断，解决这种冗余的非空判断
     * <pre>{@code
     *   if (user != null) {
     *       Address address = user.getAddress();
     *       if (address != null) {
     *           Country country = address.getCountry();
     *           if (country != null) {
     *           String code = country.getIsocode();
     *               if (code != null) {
     *               code = code.toUpperCase();
     *               }
     *           }
     *       }
     *       }
     * }</pre>
     */
    @Test
    public void whenChainingThenOk() {
        User user = new User("mail", new User.Address(new User.Country("xxxx")));
        String result = Optional.ofNullable(user).map(User::getAddress)
                .map(User.Address::getCountry).map(User.Country::getCode).map(String::toUpperCase).orElse("default");
        assertEquals(result, "XXXX");
    }
}
