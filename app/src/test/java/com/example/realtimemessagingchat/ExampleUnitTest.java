package com.example.realtimemessagingchat;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    LoginActivity loginActivity;
    FirebaseAuth firebaseAuth;
    @BeforeClass
    public static void testbeforeclass(){
    }
    @Before
    public void testbefor(){
         loginActivity=new LoginActivity();
         firebaseAuth= FirebaseAuth.getInstance();
    }
    @Test
    public void addition_isCorrect() {
        boolean result=loginActivity.Loin("ma6370612@gmail.com","mahmoud112000");
        assertEquals(false, result);
    }

    @After
    public void testerafter(){

    }
    @AfterClass
    public static void testafterclass(){
    }

}