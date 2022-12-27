package kr.or.ddit.commons.wrapper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CookieHttpServletRequestWrapper extends HttpServletRequestWrapper {
   
   private Map<String, Cookie> cookieMap; // 핵심이 되는 부분~! 우리가 사용하던 EL객체는 사실 얘였음

   public CookieHttpServletRequestWrapper(HttpServletRequest request) {
      super(request);
      cookieMap = new HashMap<>();
      Cookie[] cookies = request.getCookies();
      if(cookies != null) {
         for(Cookie tmp : cookies) {
            cookieMap.put(tmp.getName(),tmp);
         }
      }
   
   }
   
   public Cookie getCookie(String name) {
      return cookieMap.get(name);
   }
   
   public String getCookieValue(String name) {
      Cookie finded = getCookie(name);
      return Optional.ofNullable(finded)
            .map(cookie-> {
               try {
                  return URLDecoder.decode(cookie.getValue(),"UTF-8");
               } catch (UnsupportedEncodingException e) {
                  throw new RuntimeException(e);
               }
            })
            .orElse(null);
      
      
   }

}