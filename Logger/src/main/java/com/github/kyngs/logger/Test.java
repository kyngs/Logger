package com.github.kyngs.logger;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        Logger logger = LogBuilder
                .async()
                .build();
        logTest(logger);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                logger.info("lul");
            }
            logger.info("hh");
            logger.destroy();
        }, "Shutdown"));
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
