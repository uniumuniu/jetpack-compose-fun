package com.example.jetpackcomposefun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposefun.ui.theme.JetpackComposeFunTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MyScreenContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    JetpackComposeFunTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

@Composable
fun MyScreenContent(names: List<String> = List(1000) { "Android $it" }) {
    var counterState by remember {
        mutableStateOf(0)
    }

    Column(modifier = Modifier.fillMaxHeight()) {
        NamesList(names = names, modifier = Modifier.weight(1f))
        Counter(
            count = counterState,
        ) { count -> counterState = count }

        if (counterState > 5) {
            Text(text = "I love to count!")
        }
    }
}

@Composable
fun NamesList(names: List<String>, modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = names) {
            GreetingItem(name = it)
            Divider()
        }
    }
}

@Composable
fun Counter(count: Int, modifier: Modifier = Modifier, updateCount: (Int) -> Unit) {
    Button(onClick = { updateCount(count + 1) }, modifier = modifier) {
        Text("I've been clicked $count times")
    }
}

@Composable
fun GreetingItem(name: String) {
    var isSelected by remember {
        mutableStateOf(false)
    }

    val targetColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.primary else Color.Transparent,
        animationSpec = tween(durationMillis = 4000)
    )

    Surface(color = targetColor) {
        Text(
            text = "Hello $name!",
            modifier = Modifier
                .clickable { isSelected = !isSelected }
                .padding(16.5.dp)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenContent()
    }
}