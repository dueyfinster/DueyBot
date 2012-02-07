package com.dueyfinster.dueybot.data;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
/*
 * This class is so only one instance of the Persistence Manager is accessed at one time.
 */
public final class PMF {
    private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {}

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
}