package totalizatorproject.logic;

import java.lang.annotation.Retention;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface DAOAnnotation {
    String daoName();
}
