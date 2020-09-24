
import android.app.Application
import com.admin.githubrepo.GitHubApplicaton
import com.admin.githubrepo.api.GithubRepository
import com.admin.githubrepo.module.ActivityBuilderModule
import com.admin.kotlin_apigithub.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


/**
 * O componente principal da aplicação que inicializa todos os módulos dependentes
 * */

@Singleton
@Component(modules = [AppModule::class, AndroidInjectionModule::class,
    ActivityBuilderModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
    fun inject(gitHubApplicaton: GitHubApplicaton)
}