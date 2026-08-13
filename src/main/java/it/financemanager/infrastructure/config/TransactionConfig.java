package it.financemanager.infrastructure.config;

import java.lang.reflect.Method;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/** Applies a single transaction around each application use case without coupling the core to Spring. */
@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    @Bean
    DefaultPointcutAdvisor applicationUseCaseTransactionAdvisor(PlatformTransactionManager transactionManager) {
        DefaultTransactionAttribute required = new DefaultTransactionAttribute();
        required.setName("application-use-case");

        MatchAlwaysTransactionAttributeSource attributes = new MatchAlwaysTransactionAttributeSource();
        attributes.setTransactionAttribute(required);
        TransactionInterceptor interceptor = new TransactionInterceptor(transactionManager, attributes);
        return new DefaultPointcutAdvisor(new ApplicationServicePointcut(), interceptor);
    }

    private static final class ApplicationServicePointcut extends StaticMethodMatcher implements Pointcut {
        private static final String APPLICATION_SERVICES = "it.financemanager.application.service";

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return targetClass.getPackageName().equals(APPLICATION_SERVICES)
                && !targetClass.getSimpleName().equals("UserResolver");
        }

        @Override
        public ClassFilter getClassFilter() {
            return type -> type.getPackageName().equals(APPLICATION_SERVICES);
        }

        @Override
        public org.springframework.aop.MethodMatcher getMethodMatcher() {
            return this;
        }
    }
}
