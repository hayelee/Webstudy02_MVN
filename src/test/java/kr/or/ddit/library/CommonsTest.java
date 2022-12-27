package kr.or.ddit.library;

import org.apache.commons.text.CaseUtils;
import org.junit.Test;

public class CommonsTest {
   
   @Test
   public void testText() {
      String snake = "MEM_ID";
      System.out.println(CaseUtils.toCamelCase(snake, true, '_'));
   }

}