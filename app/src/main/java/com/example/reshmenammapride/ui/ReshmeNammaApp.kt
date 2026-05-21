package com.example.reshmenammapride.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reshmenammapride.data.ClimateLog
import com.example.reshmenammapride.data.ReshmeDatabase
import com.example.reshmenammapride.data.SilkBatch
import com.example.reshmenammapride.engine.ClimateAdvisor
import com.example.reshmenammapride.engine.ClimateAdvice
import com.example.reshmenammapride.engine.dateTimeText
import com.example.reshmenammapride.engine.harvestMessage
import com.example.reshmenammapride.engine.todayDate
import kotlinx.coroutines.launch
import java.util.UUID

private const val SCREEN_HOME = "HOME"
private const val SCREEN_BATCH = "BATCH"
private const val SCREEN_CLIMATE = "CLIMATE"
private const val SCREEN_HISTORY = "HISTORY"

@Composable
fun ReshmeNammaApp(database: ReshmeDatabase) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val batches by database.batchDao().observeBatches().collectAsState(initial = emptyList())
    val logs by database.climateLogDao().observeLogs().collectAsState(initial = emptyList())
    var screen by rememberSaveable { mutableStateOf(SCREEN_HOME) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF4FBF5)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppHeader()
            NavigationBar(
                selectedScreen = screen,
                onSelect = { screen = it }
            )

            when (screen) {
                SCREEN_HOME -> HomeScreen(
                    batches = batches,
                    logs = logs,
                    onAddBatch = { screen = SCREEN_BATCH },
                    onAddClimate = { screen = SCREEN_CLIMATE },
                    onShare = { shareReport(context, batches, logs) }
                )

                SCREEN_BATCH -> AddBatchScreen(
                    onSave = { batch ->
                        scope.launch {
                            database.batchDao().insertBatch(batch)
                            screen = SCREEN_HOME
                        }
                    }
                )

                SCREEN_CLIMATE -> ClimateEntryScreen(
                    batches = batches,
                    onAddBatch = { screen = SCREEN_BATCH },
                    onSave = { log ->
                        scope.launch {
                            database.climateLogDao().insertLog(log)
                            screen = SCREEN_HISTORY
                        }
                    }
                )

                SCREEN_HISTORY -> HistoryScreen(logs = logs)
            }
        }
    }
}

@Composable
private fun AppHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1B5E20))
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Reshme-Namma Pride",
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Sericulture Guard • Smart Climate Advice",
            color = Color(0xFFE8F5E9),
            fontSize = 14.sp
        )
    }
}

@Composable
private fun NavigationBar(
    selectedScreen: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        NavButton("Home", SCREEN_HOME, selectedScreen, onSelect, Modifier.weight(1f))
        NavButton("Batch", SCREEN_BATCH, selectedScreen, onSelect, Modifier.weight(1f))
        NavButton("Climate", SCREEN_CLIMATE, selectedScreen, onSelect, Modifier.weight(1f))
        NavButton("History", SCREEN_HISTORY, selectedScreen, onSelect, Modifier.weight(1f))
    }
}

@Composable
private fun NavButton(
    title: String,
    screenName: String,
    selectedScreen: String,
    onSelect: (String) -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = { onSelect(screenName) },
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selectedScreen == screenName) Color(0xFF2E7D32) else Color(0xFFC8E6C9),
            contentColor = if (selectedScreen == screenName) Color.White else Color(0xFF1B5E20)
        )
    ) {
        Text(text = title, fontSize = 12.sp)
    }
}

@Composable
private fun HomeScreen(
    batches: List<SilkBatch>,
    logs: List<ClimateLog>,
    onAddBatch: () -> Unit,
    onAddClimate: () -> Unit,
    onShare: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Dashboard",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            InfoCard("Batches", batches.size.toString(), Modifier.weight(1f))
            InfoCard("Climate Logs", logs.size.toString(), Modifier.weight(1f))
        }

        if (logs.isNotEmpty()) {
            val latest = logs.first()
            ClimateDialCard(
                status = latest.status,
                title = "Latest Alert",
                subtitle = "${latest.batchName} • ${latest.stage}",
                advice = latest.advice
            )
        } else {
            EmptyCard(
                title = "No climate entry yet",
                message = "Add temperature and humidity to get smart advice."
            )
        }

        Text(
            text = "Active Batches",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF263238)
        )

        if (batches.isEmpty()) {
            EmptyCard(
                title = "No batch added",
                message = "Start by adding a silkworm batch with date, breed and instar stage."
            )
        } else {
            batches.forEach { batch ->
                BatchCard(batch = batch)
            }
        }

        Button(
            onClick = onAddBatch,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Text("Add New Batch")
        }

        Button(
            onClick = onAddClimate,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00695C))
        ) {
            Text("Enter Climate Details")
        }

        Button(
            onClick = onShare,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF795548))
        ) {
            Text("Share Summary")
        }
    }
}

@Composable
private fun InfoCard(
    title: String,
    value: String,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )
            Text(text = title, fontSize = 13.sp)
        }
    }
}

@Composable
private fun EmptyCard(title: String, message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 17.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = message, color = Color(0xFF546E7A))
        }
    }
}

@Composable
private fun ClimateDialCard(
    status: String,
    title: String,
    subtitle: String,
    advice: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = statusBackground(status)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .background(statusColor(status), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (status) {
                        "SAFE" -> "OK"
                        "CAUTION" -> "!"
                        "DANGER" -> "!!"
                        else -> "?"
                    },
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "$title: $status",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusColor(status)
                )
                Text(text = subtitle, fontSize = 13.sp, color = Color(0xFF455A64))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = advice, fontSize = 14.sp)
            }
        }
    }
}

@Composable
private fun BatchCard(batch: SilkBatch) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = batch.batchName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
            )
            Spacer(modifier = Modifier.height(4.dp))
            DetailRow("Breed", batch.breed)
            DetailRow("Start Date", batch.startDate)
            DetailRow("Stage", batch.stage)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = harvestMessage(batch.startDate),
                color = Color(0xFF00695C),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun AddBatchScreen(onSave: (SilkBatch) -> Unit) {
    var batchName by rememberSaveable { mutableStateOf("") }
    var breed by rememberSaveable { mutableStateOf("") }
    var startDate by rememberSaveable { mutableStateOf(todayDate()) }
    var selectedStage by rememberSaveable { mutableStateOf(ClimateAdvisor.stages.first()) }
    var error by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Add Silkworm Batch",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20)
        )

        OutlinedTextField(
            value = batchName,
            onValueChange = { batchName = it },
            label = { Text("Batch Name") },
            placeholder = { Text("Example: Batch 1") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = breed,
            onValueChange = { breed = it },
            label = { Text("Breed") },
            placeholder = { Text("Example: Mysore Silk") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = startDate,
            onValueChange = { startDate = it },
            label = { Text("Start Date") },
            placeholder = { Text("yyyy-MM-dd") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Select Instar Stage", fontWeight = FontWeight.Bold)
        StageSelector(selectedStage = selectedStage, onStageSelected = { selectedStage = it })

        if (error.isNotBlank()) {
            Text(text = error, color = Color(0xFFB71C1C), fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = {
                error = when {
                    batchName.isBlank() -> "Enter batch name."
                    breed.isBlank() -> "Enter breed name."
                    startDate.isBlank() -> "Enter start date."
                    else -> ""
                }

                if (error.isBlank()) {
                    onSave(
                        SilkBatch(
                            id = UUID.randomUUID().toString(),
                            batchName = batchName.trim(),
                            breed = breed.trim(),
                            startDate = startDate.trim(),
                            stage = selectedStage,
                            createdAt = System.currentTimeMillis()
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Text("Save Batch")
        }
    }
}

@Composable
private fun ClimateEntryScreen(
    batches: List<SilkBatch>,
    onAddBatch: () -> Unit,
    onSave: (ClimateLog) -> Unit
) {
    if (batches.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "No Batch Found",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
            )
            Text("Please add a silkworm batch before adding climate details.")
            Button(
                onClick = onAddBatch,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text("Add Batch")
            }
        }
        return
    }

    var selectedBatchId by rememberSaveable { mutableStateOf(batches.first().id) }
    LaunchedEffect(batches) {
        if (batches.none { it.id == selectedBatchId }) {
            selectedBatchId = batches.first().id
        }
    }

    val selectedBatch = batches.firstOrNull { it.id == selectedBatchId } ?: batches.first()
    var selectedStage by rememberSaveable { mutableStateOf(selectedBatch.stage) }
    var temperature by rememberSaveable { mutableStateOf("") }
    var humidity by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf("") }
    var preview by remember { mutableStateOf<ClimateAdvice?>(null) }

    LaunchedEffect(selectedBatchId) {
        selectedStage = selectedBatch.stage
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Climate Entry",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20)
        )

        Text("Select Batch", fontWeight = FontWeight.Bold)
        batches.forEach { batch ->
            Button(
                onClick = { selectedBatchId = batch.id },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedBatchId == batch.id) Color(0xFF2E7D32) else Color(0xFFC8E6C9),
                    contentColor = if (selectedBatchId == batch.id) Color.White else Color(0xFF1B5E20)
                )
            ) {
                Text(batch.batchName)
            }
        }

        Text("Select Current Instar Stage", fontWeight = FontWeight.Bold)
        StageSelector(selectedStage = selectedStage, onStageSelected = { selectedStage = it })

        Text(
            text = ClimateAdvisor.idealRangeText(selectedStage),
            color = Color(0xFF00695C),
            fontWeight = FontWeight.SemiBold
        )

        OutlinedTextField(
            value = temperature,
            onValueChange = { temperature = it },
            label = { Text("Temperature °C") },
            placeholder = { Text("Example: 27") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = humidity,
            onValueChange = { humidity = it },
            label = { Text("Humidity %") },
            placeholder = { Text("Example: 85") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        if (error.isNotBlank()) {
            Text(text = error, color = Color(0xFFB71C1C), fontWeight = FontWeight.Bold)
        }

        preview?.let { result ->
            ClimateDialCard(
                status = result.status,
                title = "Smart Advice",
                subtitle = "$selectedStage • ${temperature}°C • ${humidity}%",
                advice = result.advice
            )
        }

        Button(
            onClick = {
                val tempValue = temperature.toFloatOrNull()
                val humidityValue = humidity.toFloatOrNull()

                if (tempValue == null || humidityValue == null) {
                    error = "Enter valid temperature and humidity."
                    preview = null
                } else {
                    val result = ClimateAdvisor.analyze(selectedStage, tempValue, humidityValue)
                    if (result.status == "INVALID") {
                        error = result.advice
                        preview = null
                    } else {
                        error = ""
                        preview = result
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00695C))
        ) {
            Text("Check Climate")
        }

        Button(
            onClick = {
                val tempValue = temperature.toFloatOrNull()
                val humidityValue = humidity.toFloatOrNull()

                if (tempValue == null || humidityValue == null) {
                    error = "Enter valid temperature and humidity."
                    return@Button
                }

                val result = ClimateAdvisor.analyze(selectedStage, tempValue, humidityValue)
                if (result.status == "INVALID") {
                    error = result.advice
                    return@Button
                }

                onSave(
                    ClimateLog(
                        id = UUID.randomUUID().toString(),
                        batchId = selectedBatch.id,
                        batchName = selectedBatch.batchName,
                        temperature = tempValue,
                        humidity = humidityValue,
                        stage = selectedStage,
                        status = result.status,
                        advice = result.advice,
                        loggedAt = System.currentTimeMillis()
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Text("Save Climate Entry")
        }
    }
}

@Composable
private fun StageSelector(
    selectedStage: String,
    onStageSelected: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        ClimateAdvisor.stages.chunked(2).forEach { rowStages ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowStages.forEach { stage ->
                    Button(
                        onClick = { onStageSelected(stage) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedStage == stage) Color(0xFF2E7D32) else Color(0xFFC8E6C9),
                            contentColor = if (selectedStage == stage) Color.White else Color(0xFF1B5E20)
                        )
                    ) {
                        Text(stage, fontSize = 12.sp)
                    }
                }
                if (rowStages.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun HistoryScreen(logs: List<ClimateLog>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Climate History",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20)
        )

        if (logs.isEmpty()) {
            EmptyCard("No history", "Climate entries will appear here after saving.")
        } else {
            logs.forEach { log ->
                ClimateHistoryCard(log = log)
            }
        }
    }
}

@Composable
private fun ClimateHistoryCard(log: ClimateLog) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = statusBackground(log.status)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = log.status,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = statusColor(log.status)
            )
            Spacer(modifier = Modifier.height(4.dp))
            DetailRow("Batch", log.batchName)
            DetailRow("Stage", log.stage)
            DetailRow("Temperature", "${log.temperature}°C")
            DetailRow("Humidity", "${log.humidity}%")
            DetailRow("Time", dateTimeText(log.loggedAt))
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            Text("Advice", fontWeight = FontWeight.Bold)
            Text(log.advice)
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(115.dp),
            color = Color(0xFF263238)
        )
        Text(text = value, modifier = Modifier.weight(1f))
    }
}

private fun statusColor(status: String): Color {
    return when (status) {
        "SAFE" -> Color(0xFF1B5E20)
        "CAUTION" -> Color(0xFFF57F17)
        "DANGER" -> Color(0xFFB71C1C)
        else -> Color(0xFF263238)
    }
}

private fun statusBackground(status: String): Color {
    return when (status) {
        "SAFE" -> Color(0xFFE3F6E5)
        "CAUTION" -> Color(0xFFFFF4D8)
        "DANGER" -> Color(0xFFFFE1E5)
        else -> Color.White
    }
}

private fun shareReport(
    context: Context,
    batches: List<SilkBatch>,
    logs: List<ClimateLog>
) {
    val latest = logs.firstOrNull()
    val report = buildString {
        appendLine("Reshme-Namma Pride - Sericulture Guard Report")
        appendLine("--------------------------------------------")
        appendLine("Total Batches: ${batches.size}")
        appendLine("Total Climate Logs: ${logs.size}")
        appendLine()
        if (latest != null) {
            appendLine("Latest Climate Alert")
            appendLine("Batch: ${latest.batchName}")
            appendLine("Stage: ${latest.stage}")
            appendLine("Temperature: ${latest.temperature}°C")
            appendLine("Humidity: ${latest.humidity}%")
            appendLine("Status: ${latest.status}")
            appendLine("Advice: ${latest.advice}")
            appendLine("Time: ${dateTimeText(latest.loggedAt)}")
        } else {
            appendLine("No climate entries added yet.")
        }
    }

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Reshme-Namma Pride Report")
        putExtra(Intent.EXTRA_TEXT, report)
    }
    context.startActivity(Intent.createChooser(intent, "Share Report"))
}
