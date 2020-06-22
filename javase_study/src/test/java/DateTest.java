import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author chenzy
 *
 * @since 2020/6/20
 */
public class DateTest {


    @Test
    public void newDateTest() throws InterruptedException {
        {
            var date = LocalDateTime.now();
            System.out.println(date);
        }

        {
            var dateTime = LocalDateTime.of(2010, 4, 4, 12, 23, 1);
            System.out.println(dateTime);
        }
        {
            var date = LocalDateTime.now();
            date.plusYears(2);//加两年
            date.minusMonths(2);//减两月
            System.out.println(date);
            date.getYear();
            date.getMonthValue();
            date.getDayOfMonth();
            date.getHour();
        }
        //时间戳:已Unix元年1970年1月1日00:00:00到某个时间之间的毫秒值
        {
            var instant = Instant.now();//默认获取UTC时区
            var dateTime = instant.atOffset(ZoneOffset.ofHours(8));
            System.out.println(dateTime);
            dateTime.toEpochSecond();
            instant.toEpochMilli();//毫秒

            var instant1 = Instant.ofEpochSecond(1);//元年加1秒
        }
        {
            //Duration 时间之间间隔
            //Period 日期之间间隔
            var instant = Instant.now();
            Thread.sleep(1000);
            var instant1 = Instant.now();
            var duration = Duration.between(instant, instant1);
            System.out.println(duration.getNano());//纳秒
            duration.toMillis();//毫秒
        }
        {
            var localDateTime = LocalDateTime.now();
        }
        {
            //时间校正器TemporalAdjuster
            var localDateTime = LocalDateTime.now();
            var dateTime = localDateTime.withDayOfMonth(10);

            localDateTime.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));//下个周日

            //自定义校正器，下一个工作日
            localDateTime.with(temporal -> {
                var dateTime1 = (LocalDateTime) temporal;
                var dayOfWeek = dateTime1.getDayOfWeek();
                switch (dayOfWeek) {
                    case FRIDAY:
                        return dateTime1.plusDays(3);
                    case SATURDAY:
                        return dateTime1.plusDays(2);
                    default:
                        return dateTime1.plusDays(1);
                }
            });
        }
        {
//            DateTimeFormatter 格式化时间
//            var dateTimeFormatter=DateTimeFormatter.ISO_DATE_TIME;
            var dateTimeFormatter = DateTimeFormatter.ISO_DATE;
            LocalDateTime now = LocalDateTime.now();
            System.out.println(now.format(dateTimeFormatter));

            var dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
            var dataText = dateTimeFormatter1.format(now);
            System.out.println(dataText);

            System.out.println(LocalDateTime.parse(dataText,dateTimeFormatter1));
        }

        {
            //时区
            var zoneIds=ZoneId.getAvailableZoneIds();
            var localDateTime=LocalDateTime.now(ZoneId.of("Europe/Tallinn"));
            System.out.println(localDateTime);

            var zonedDateTime=LocalDateTime.now().atZone(ZoneId.of("Europe/Tallinn"));
            System.out.println(zonedDateTime);
        }

    }

    /*java8后解决SimpleDateFormat线程安全问题*/
    @Test
    public void test() throws ExecutionException, InterruptedException {
        var pool = Executors.newFixedThreadPool(10);
        var simpleDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = "20161218";
        simpleDateFormat.parse(date);
        var task = new Callable<LocalDate>() {
            @Override
            public LocalDate call() throws Exception {
                return LocalDate.parse(date, simpleDateFormat);
            }
        };
        var results = new ArrayList<Future<LocalDate>>();
        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }
        for (var fu : results) {
            System.out.println(fu.get());
        }
        pool.shutdown();
    }


    //java8以前解决SimpleDateFormat线程安全问题
    @Test
    public void testSimpleDateFormat2() throws ExecutionException, InterruptedException, ParseException {
        var pool = Executors.newFixedThreadPool(10);
        var simpleDateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd"));
        String date = "20161218";
        simpleDateFormat.get().parse(date);


        var task = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return simpleDateFormat.get().parse(date);
            }
        };
        var results = new ArrayList<Future<Date>>();
        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }
        for (var fu : results) {
            System.out.println(fu.get());
        }
        pool.shutdown();
    }

    //SimpleDateFormat线程安全问题
    @Test
    public void testSimpleDateFormat() throws ExecutionException, InterruptedException {
        var simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        var pool = Executors.newFixedThreadPool(10);
        var task = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return simpleDateFormat.parse("20161218");
            }
        };
        var results = new ArrayList<Future<Date>>();
        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }
        for (var fu : results) {
            System.out.println(fu.get());
        }
    }
}
