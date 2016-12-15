package com.github.winteryoung.yanwte.spring.internals

import com.github.winteryoung.yanwte.YanwteException
import com.github.winteryoung.yanwte.YanwtePlugin
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent

/**
 * Define a bean of this class in your spring application context.
 * This hook will integrate Spring to Yanwte.
 *
 * **Note**, the [basePackage] of your program is required to initialize this class.
 * Be *very careful* to set this right. You may cause other part of the system wrong
 * if you set this wrong. For example, if your classes are all located under
 * `com.yourcompany.yourdepartment.yourproject`, then set this to [basePackage].
 *
 * @author Winter Young
 * @since 2016/1/23
 */
class YanwteSpringHook : ApplicationListener<ContextRefreshedEvent> {
    /**
     * The base package of your program.
     */
    var basePackage: String? = null

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val packageName = basePackage ?: throw YanwteException("The base package of your program is required")
        val applicationContext = event.applicationContext

        val springPlugin = SpringPlugin(applicationContext)
        YanwtePlugin.registerPlugin(springPlugin, packageName)

        if (started) {
            return
        }
        started = true
    }

    companion object {
        /**
         * If this hook has been started.
         */
        @Volatile
        var started = false
            private set
    }
}