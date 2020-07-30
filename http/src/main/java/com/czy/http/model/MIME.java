package com.czy.http.model;

import com.czy.http.enums.MIMEEnum;

/**
 * @author chenzy
 * @date 2020-07-30
 *
 * q:相对品质因子: 每个类型可以被赋予一个相关的质量值，该质量值表示用户对该类型的偏好的估计。质量值默认为“q = 1”
 */

public record MIME(MIMEEnum mime,double q) {
}
