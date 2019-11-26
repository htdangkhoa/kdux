# kdux
![Platform](https://img.shields.io/badge/Platform-Android%206.0-36da7e?logo=android)
![Kotlin](https://img.shields.io/badge/Kotlin-1.3.50-orange?logo=kotlin)
[![Release](https://jitpack.io/v/htdangkhoa/kdux.svg)](https://jitpack.io/#htdangkhoa/kdux)

Android + Kotlin + Redux = ❤️

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
    implementation 'com.github.htdangkhoa:kdux:<latest_version>'
}
```

## Usage
- **State:**
  ```kotlin
  data class CounterState(val number: Int = 0): State
  ```

- **Action:**
  ```kotlin
  sealed class CounterAction: Action {
      object INCREASE: CounterAction()

      object DECREASE: CounterAction()

      companion object {
          fun increaseAction(dispatch: Dispatch) = dispatch(INCREASE)

          fun decreaseAction(dispatch: Dispatch) = dispatch(DECREASE)
      }
  }
  ```

- **Reducer:**
  ```kotlin
  class CounterReducer: Reducer<CounterState> {
      override fun reduce(state: CounterState, action: Action): CounterState {
          return when (action) {
              CounterAction.INCREASE -> state.copy(number = state.number + 1)

              CounterAction.DECREASE -> state.copy(number = state.number - 1)

              else -> state
          }
      }
  }
  ```

- **Activity:**
  ```kotlin
  class CounterActivity: AppCompatActivity(), Enhancer<CounterState> {
      private val store: Store<CounterState> by lazy {
          Store(
              CounterReducer(),
              CounterState()
              // applyMiddleware(...)
          )
      }

      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_counter)

          btnIncrease.setOnClickListener {
              CounterAction.increaseAction { store.dispatch(it) }
          }

          btnDecrease.setOnClickListener {
              CounterAction.decreaseAction { store.dispatch(it) }
          }
      }

      override fun onStart() {
          super.onStart()

          store.subscribe(this)
      }

      override fun onStop() {
          super.onStop()

          store.unsubscribe(this)
      }

      override fun enhance(state: CounterState) {
          txtNumber.text = "${state.number}"
      }
  }
  ```

- **Middleware:** [(List middlewares)](https://github.com/htdangkhoa/kdux/tree/master/kdux/src/main/java/com/github/htdangkhoa/kdux/middlewares)
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

## Examples
- [Counter (with DevTools)](https://github.com/htdangkhoa/kdux/tree/master/app/src/main/java/com/github/htdangkhoa/demo/ui/counter)
- [Counter (with Thunk)](https://github.com/htdangkhoa/kdux/tree/master/app/src/main/java/com/github/htdangkhoa/demo/ui/counter_thunk)
- [Todo](https://github.com/htdangkhoa/kdux/tree/master/app/src/main/java/com/github/htdangkhoa/demo/ui/todo)
- [Todo (Coroutines + ViewModel)](https://github.com/htdangkhoa/kdux/tree/master/app/src/main/java/com/github/htdangkhoa/demo/ui/todo_viewmodel)

## Bonus
- **Thunk:**
  ```kotlin
  // Action
  sealed class CounterAction: Action {
      object INCREASE: CounterAction()

      object DECREASE: CounterAction()

      companion object {
          fun increaseAction(): Action = KduxThunkAction(object: Thunk {
              override fun invoke(dispatcher: Dispatcher, state: State) {
                  dispatcher.dispatch(INCREASE)
              }
          })

          fun decreaseAction(): Action = KduxThunkAction(object: Thunk {
              override fun invoke(dispatcher: Dispatcher, state: State) {
                  dispatcher.dispatch(DECREASE)
              }
          })
      }
  }

  // Store
  private val store = Store(
      ...,
      applyMiddleware(KduxThunk())
  )

  store.dispatch(increaseAction())

  store.dispatch(decreaseAction())
  ```

- **Logger:**
  ```kotlin
  private val store: Store<CounterState> by lazy {
      Store(
          ...,
          applyMiddleware(KduxLogger())
      )
  }
  ```

- **Debounce:**
  ```kotlin
  private val store: Store<CounterState> by lazy {
      Store(
          ...,
          applyMiddleware(KduxDebounce(debounce = 500)) // Range of debounce from 0 to 30000.
      )
  }
  ```

- **DevTools:**
  ```kotlin
  private val store: Store<CounterState> by lazy {
      composeWithDevTools(
          Store(
              ...,
              // applyMiddleware(...)
          )
      )
  }

  // UNDO
  store.dispatch(KduxDevToolAction.UNDO)

  // REDO
  store.dispatch(KduxDevToolAction.REDO)

  // RESET
  store.dispatch(KduxDevToolAction.RESET)
  ```

## License
    MIT License

    Copyright (c) 2019 Huỳnh Trần Đăng Khoa

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
