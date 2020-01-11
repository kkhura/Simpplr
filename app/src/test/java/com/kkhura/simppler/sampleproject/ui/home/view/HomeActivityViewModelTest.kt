package com.kkhura.simppler.sampleproject.ui.home.view

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.support.annotation.NonNull
import com.kkhura.simppler.sampleproject.shareddata.SimpplerServices
import com.kkhura.simppler.sampleproject.ui.home.model.AlbumContainerModel
import com.kkhura.simppler.sampleproject.ui.home.model.UserModel
import com.kkhura.simppler.sampleproject.ui.home.repo.HomeActivityRepo
import com.kkhura.simppler.sampleproject.ui.home.repo.MockData
import com.kkhura.simppler.sampleproject.ui.main.view.HomeActivityViewModel
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler.ExecutorWorker
import io.reactivex.plugins.RxJavaPlugins
import org.junit.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


class HomeActivityViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var services: SimpplerServices

    lateinit var homeActivityViewModel: HomeActivityViewModel

    @Before
    fun setUp() {
        MockData.isMockData = true
        MockitoAnnotations.initMocks(this)
        this.homeActivityViewModel = HomeActivityViewModel(HomeActivityRepo(services))
        setUpRxSchedulers()
    }

    fun setUpRxSchedulers() {
        val immediate: Scheduler = object : Scheduler() {
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, delay, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorWorker(Executor { obj: Runnable -> obj.run() })
            }
        }
        RxJavaPlugins.setInitIoSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
    }

    @Test
    fun getUser() {
        val value = homeActivityViewModel.getUser().value
        if (value is UserModel) {
            Assert.assertEquals("nullvoidinfinity", value.display_name)
            Assert.assertEquals("piyushrajput@null.net", value.email)
        }
    }

    @Test
    fun getAlbum() {
        val value = homeActivityViewModel.getAlbums(0, 10).value
        if (value is AlbumContainerModel) {
            Assert.assertEquals(5, value.total)
            Assert.assertEquals("Led Zeppelin IV (Deluxe Edition)", value.items[0].album.name)
            Assert.assertEquals("album", value.items[0].album.type)
        }
    }

    @Test
    fun getTrack() {
        val value = homeActivityViewModel.getAlbums(0, 10).value
        if (value is AlbumContainerModel) {
            Assert.assertEquals(16, value.items[0].album.tracks.items.size)
            Assert.assertEquals("Black Dog - Remaster", value.items[0].album.tracks.items[0].name)
        }
    }

}