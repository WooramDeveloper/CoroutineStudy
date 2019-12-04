package woo.coroutinestudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private const val TAG: String = "Wooram"

class MainActivity : AppCompatActivity() {

    private val mainTask: Executor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainTask.execute {
            //doWork1()
            //doWork2()
            //doWork3()
            //doWork4()
            Log.i(TAG, "1) thread id :" + Thread.currentThread().id)
            doWork5()
            //doWork6()
        }

    }

    // 예제1) GlobalScope 를 사용한 Concurrency 구현.
    fun doWork1() = runBlocking {
        GlobalScope.launch {
            delay(1000L)
            Log.i(TAG, "World")
        }
        Log.i(TAG, "Hello")
    }

    // 예제2) runBlocking 을 사용한 Thread Block.
    fun doWork2() = runBlocking {
        GlobalScope.launch {
            delay(1000L)
            Log.i(TAG, "World")
        }
        Log.i(TAG, "Hello")
        runBlocking {
            delay(2000L)
            Log.i(TAG, "!!")
        }
    }

    // 예제3) join 을 사용한 job 웨이팅.
    fun doWork3() = runBlocking {
        val job = GlobalScope.launch {
            delay(1000L)
            Log.i(TAG, "World")
        }
        Log.i(TAG, "Hello")
        job.join()
        Log.i(TAG, "!!")
    }

    // runBlocking 의 coroutine builder 를 사용한 코루틴 생성. not complete until all the coroutines launched in its scope complete.
    fun doWork4() = runBlocking {
        launch {
            delay(1000L)
            Log.i(TAG, "World")
        }
        Log.i(TAG, "Hello")
    }

    // 또 다른 coroutine scope 생성하기.
    fun doWork5() = runBlocking {
        Log.i(TAG, "2) thread id :" + Thread.currentThread().id)
        launch {
            Log.i(TAG, "3) thread id :" + Thread.currentThread().id)
            //delay(1000L)
            //Log.i(TAG, "(1) Task from runBlocking")
        }
        runBlocking {
            Log.i(TAG, "4) thread id :" + Thread.currentThread().id)
            launch {
                Log.i(TAG, "5) thread id :" + Thread.currentThread().id)
                //delay(5000L)
                //Log.i(TAG, "(3) Task from nested launch")
            }
            //delay(2000L)
            //Log.i(TAG, "(2) Task from coroutine scope")
        }
        //Log.i(TAG, "(4) Coroutine scope is over")
    }

    // suspend fun 구현. 내부적으로 suspending function을 사용할 수 있음. (ex : delay, coroutineScope)
    fun doWork6() = runBlocking {
        launch {
            doWorld()
        }
        Log.i(TAG, "Hello")
    }

    suspend fun doWorld() {
        delay(1000L)
        Log.i(TAG, "World")
        coroutineScope {

        }
    }

}
