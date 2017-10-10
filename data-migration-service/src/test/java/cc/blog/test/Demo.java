package cc.blog.test;

import com.sinosoft.datamigration.dao.IDynamicDAO;
import com.sinosoft.datamigration.dao.impl.DynamicDAOImpl;
import com.sinosoft.datamigration.util.DBManager;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvis on 2017/9/1.
 */
public class Demo {

    @Test
    public void fun() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String url = "";

        Connection conn = DBManager.getConnection(url, "root", "8023");

    }
}
