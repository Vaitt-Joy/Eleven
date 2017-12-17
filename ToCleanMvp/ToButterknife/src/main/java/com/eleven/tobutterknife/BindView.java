package com.eleven.tobutterknife;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author vic Zhou
 * @time 2017-12-17 18:17
 * <p>
 * Bind a field to the view for the specified ID. The view will automatically be cast to the field
 * type.
 * <pre><code>
 * {@literal @}Bind(R.id.title) TextView title;
 * </code></pre>
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BindView {
    /**
     * View ID to which the field will be bound.
     */
    int value();
}

