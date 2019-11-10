# kdux [![Release](https://jitpack.io/v/htdangkhoa/kdux.svg)](https://jitpack.io/#htdangkhoa/kdux)
Redux for Android &amp; Kotlin

## Installation
Add the JitPack repository to your root build.gradle at the end of repositories:
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Add the dependency:
```gradle
dependencies {
    implementation 'com.github.htdangkhoa:kdux:1.0.0'
}
```

## Usage
- State:
    ```kotlin
    data class DemoState(isLoading: Boolean): State
    ```
- Action:
    ```kotlin
    sealed class DemoAction: Action {
        data class IS_LOADING(val payload: Boolean = false): DemoAction()

        companion object {
            fun updateLoadingAction(isLoading: Boolean, dispatch: (action: Action) -> Unit) {
                return dispatch(IS_LOADING(isLoading))
            }
        }
    }
    ```
- Reducer:
    ```kotlin
    class DemoReducer: Reducer<DemoState> {
        override fun reduce(state: DemoState, action: Action): DemoState {
            return when(action) {
                is DemoAction.IS_LOADING -> {
                    state.copy(isLoading = action.payload)
                }
                else -> state
            }
        }
    }
    ```
- Activity:
    ```kotlin
    class DemoActivity: AppCompatActivity(), Enhancer<DemoState> {
        private val store: Store<DemoState> by lazy {
            Store(
                DemoReducer(),
                DemoState()
            )
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_demo)
        }

        override fun onStart() {
            super.onStart()

            store.subscribe(this)
        }

        override fun onStop() {
            super.onStop()

            store.unsubscribe(this)
        }

        override fun enhance(state: DemoState) {
            progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        }
    }
    ```
- Middleware:
    ```kotlin
    class DemoMiddleware<S: State>: Middleware<S> {
        override fun onBeforeDispatch(store: Store<S>, action: Action) {
            // Doing somthings...
        }
        
        override fun onDispatch(store: Store<S>, action: Action, next: Dispatcher) {
            // Doing somthings...
            
            next.dispatch(action)
        }
        
        override fun onAfterDispatch(store: Store<S>, action: Action) {
            // Doing somthings...
        }
    }
    ```

## Bonus
- Logger:
    ```kotlin
    private val store: Store<DemoState> by lazy {
        Store(
            DemoReducer(),
            DemoState(),
            applyMiddleware(KduxLogger())
        )
    }
    ```
- DevTools:
    ```kotlin
    private val store: Store<DemoState> by lazy {
        composeWithDevTools(
            Store(DemoReducer(), DemoState())
        )
    }
    
    // UNDO
    store.dispatch(KDuxDevToolAction.UNDO)
    
    // REDO
    store.dispatch(KDuxDevToolAction.REDO)
    
    // RESET
    store.dispatch(KDuxDevToolAction.RESET)
    ```
