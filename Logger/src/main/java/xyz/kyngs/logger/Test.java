package xyz.kyngs.logger;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        //Logger logger = LogBuilder
        //        .async()
        //        .build();
        //logTest(logger);
        //Thread.sleep(1000);
        ////logger.destroy();
//        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> Logger.ERR.print(e.getMessage()));
        Logger logger = LogBuilder
                .async()
                .build();
        logTest(logger);
        logger.destroy();
    }

    private static void logTest(Logger logger) {
        logger.info("info");
        logger.warn("warning");
        logger.error("error");
        logger.info("§aHello §cWorld");
        logger.info("§§");
        logger.info("§§§§§§§§§§");
    }

}
