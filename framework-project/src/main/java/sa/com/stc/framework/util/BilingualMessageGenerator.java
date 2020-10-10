/*
 * Copyright @2020 stc. All Rights reserved.
 * https://www.stc.com.sa
 */

package sa.com.stc.framework.util;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import sa.com.stc.framework.dto.BilingualMessage;


/**
 * Utility class to construct bilingual messages (english and arabic).
 */
@Component
@RequiredArgsConstructor
public class BilingualMessageGenerator {

  private final MessageSource messageSource;

  public BilingualMessage generateMessage(String messageKey, String[] parameters) {
    return BilingualMessage.builder()
        .english(messageSource.getMessage(messageKey, parameters, Locale.ENGLISH))
        .arabic(messageSource.getMessage(messageKey, parameters, new Locale("ar")))
        .build();
  }
}
