package com.backchannel.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Contacts
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.QrCode2
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.UUID

private val Night = Color(0xFF090D18)
private val Panel = Color(0xFF121A2B)
private val PanelSoft = Color(0xFF182238)
private val Indigo = Color(0xFF8585E8)
private val Lavender = Color(0xFFC4BDF5)
private val Muted = Color(0xFF8994AD)
private val MainText = Color(0xFFF1F2FA)
private val Green = Color(0xFF7FC7A7)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                BackchannelApp()
            }
        }
    }
}

private enum class Page { HOME, CONNECTIONS, SECURITY, SETTINGS, PROFILE }

private data class Conversation(
    val initials: String,
    val title: String,
    val preview: String,
    val time: String,
    val tint: Color
)

@Composable
private fun BackchannelApp() {
    var page by remember { mutableStateOf(Page.HOME) }
    var selectedChat by remember { mutableStateOf<String?>(null) }
    val localId = remember {
        "BC-" + UUID.randomUUID().toString().replace("-", "").take(8).uppercase()
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Night) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                if (selectedChat != null) {
                    ChatPreviewScreen(
                        title = selectedChat!!,
                        onBack = { selectedChat = null }
                    )
                } else {
                    when (page) {
                        Page.HOME -> HomeScreen(localId, onOpenChat = { selectedChat = it })
                        Page.CONNECTIONS -> ConnectionsScreen()
                        Page.SECURITY -> SecurityScreen()
                        Page.SETTINGS -> SettingsScreen()
                        Page.PROFILE -> ProfileScreen(localId)
                    }
                }
            }
            if (selectedChat == null) {
                BottomBar(page = page, onSelect = { page = it })
            }
        }
    }
}

@Composable
private fun BrandHeader(subtitle: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(42.dp).clip(RoundedCornerShape(14.dp))
                .background(PanelSoft).border(1.dp, Indigo.copy(alpha = .35f), RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Rounded.Shield, contentDescription = null, tint = Lavender, modifier = Modifier.size(23.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text("BACKCHANNEL", color = MainText, fontSize = 15.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            Text(subtitle, color = Muted, fontSize = 11.sp, letterSpacing = 1.5.sp)
        }
        Box(
            modifier = Modifier.clip(CircleShape).background(Panel).padding(horizontal = 11.dp, vertical = 7.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(6.dp).clip(CircleShape).background(Green))
                Spacer(Modifier.width(7.dp))
                Text("PRIVATE MODE", color = Green, fontSize = 9.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.sp)
            }
        }
    }
}

@Composable
private fun HomeScreen(localId: String, onOpenChat: (String) -> Unit) {
    val chats = listOf(
        Conversation("A", "Alex", "Your messages stay between devices.", "9:42 PM", Indigo),
        Conversation("M", "Maya", "Sent you a file", "Yesterday", Color(0xFF9B8ACB)),
        Conversation("J", "Jordan", "Got it. Talk soon.", "Mon", Color(0xFF719CB4))
    )
    Column(Modifier.fillMaxSize()) {
        BrandHeader("P2P CONNECTED")
        Column(Modifier.padding(horizontal = 22.dp)) {
            Text("Your space.", color = MainText, fontSize = 29.sp, fontWeight = FontWeight.SemiBold)
            Text("Quiet conversations. On your terms.", color = Muted, fontSize = 13.sp)
            Spacer(Modifier.height(22.dp))
            Row(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp))
                    .background(Panel).clickable { onOpenChat("Your identity") }.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.size(42.dp).clip(CircleShape).background(Indigo.copy(alpha = .16f)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Rounded.Lock, null, tint = Lavender)
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text("Your BACKCHANNEL ID", color = Muted, fontSize = 10.sp, letterSpacing = 1.sp)
                    Text(localId, color = MainText, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.sp)
                }
                Icon(Icons.Rounded.QrCode2, null, tint = Lavender, modifier = Modifier.size(25.dp))
            }
            Spacer(Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Messages", color = MainText, fontSize = 19.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                Text("NEW CHAT", color = Lavender, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Spacer(Modifier.width(8.dp))
                Icon(Icons.Rounded.Add, null, tint = Lavender)
            }
            Spacer(Modifier.height(12.dp))
        }
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 22.dp, vertical = 2.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            items(chats) { chat ->
                Row(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(17.dp))
                        .background(Panel).clickable { onOpenChat(chat.title) }.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.size(45.dp).clip(CircleShape).background(chat.tint.copy(alpha = .18f)), contentAlignment = Alignment.Center) {
                        Text(chat.initials, color = Lavender, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(chat.title, color = MainText, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Text(chat.preview, color = Muted, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    Text(chat.time, color = Muted, fontSize = 10.sp)
                }
            }
            item {
                Spacer(Modifier.height(8.dp))
                Text("New conversations will appear here after you connect with someone.", color = Muted, fontSize = 11.sp, modifier = Modifier.fillMaxWidth().padding(10.dp))
            }
        }
    }
}

@Composable
private fun ChatPreviewScreen(title: String, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Rounded.ArrowBack, "Back", tint = Lavender, modifier = Modifier.clickable { onBack() })
            Spacer(Modifier.width(16.dp))
            Column {
                Text(title, color = MainText, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                Text("Conversation preview", color = Muted, fontSize = 11.sp)
            }
        }
        Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
                Icon(Icons.Rounded.Lock, null, tint = Lavender, modifier = Modifier.size(34.dp))
                Spacer(Modifier.height(14.dp))
                Text("A private space starts here.", color = MainText, fontWeight = FontWeight.SemiBold, fontSize = 17.sp)
                Spacer(Modifier.height(8.dp))
                Text("Messaging transport is not connected yet. This screen is a UI foundation, not a working chat.", color = Muted, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun ConnectionsScreen() {
    Column(Modifier.fillMaxSize()) {
        BrandHeader("YOUR NETWORK")
        Column(Modifier.padding(22.dp)) {
            Text("Connections", color = MainText, fontSize = 28.sp, fontWeight = FontWeight.SemiBold)
            Text("Bring the right people into your private space.", color = Muted, fontSize = 13.sp)
            Spacer(Modifier.height(22.dp))
            FeatureRow(Icons.Rounded.QrCode2, "Connect with QR", "Exchange identity details face to face.")
            FeatureRow(Icons.Rounded.Contacts, "Add BACKCHANNEL ID", "Connect using someone's public identity ID.")
            FeatureRow(Icons.Rounded.Add, "Nearby discovery", "Find compatible devices close to you.")
            Spacer(Modifier.height(12.dp))
            Text("Connection features are being implemented. No contacts are uploaded.", color = Muted, fontSize = 12.sp)
        }
    }
}

@Composable
private fun SecurityScreen() {
    Column(Modifier.fillMaxSize()) {
        BrandHeader("PRIVACY & PROTECTION")
        Column(Modifier.padding(22.dp)) {
            Text("Security center", color = MainText, fontSize = 27.sp, fontWeight = FontWeight.SemiBold)
            Text("Understand what protects your conversations.", color = Muted, fontSize = 13.sp)
            Spacer(Modifier.height(20.dp))
            FeatureRow(Icons.Rounded.Shield, "End-to-end encryption", "Planned: authenticated encryption between devices.")
            FeatureRow(Icons.Rounded.Lock, "Device key protection", "Planned: Android Keystore-backed identity keys.")
            FeatureRow(Icons.Rounded.QrCode2, "Verify a contact", "Planned: compare a safety fingerprint in person.")
            FeatureRow(Icons.Rounded.Security, "Local data protection", "Planned: encrypted conversation history.")
            Spacer(Modifier.height(12.dp))
            Text("These are roadmap items, not active security guarantees in this build.", color = Lavender, fontSize = 12.sp)
        }
    }
}

@Composable
private fun SettingsScreen() {
    Column(Modifier.fillMaxSize()) {
        BrandHeader("PREFERENCES")
        Column(Modifier.padding(22.dp)) {
            Text("Settings", color = MainText, fontSize = 28.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(20.dp))
            FeatureRow(Icons.Rounded.Lock, "App lock", "A local app lock is planned.")
            FeatureRow(Icons.Rounded.Tune, "Appearance", "Midnight theme foundation.")
            FeatureRow(Icons.Rounded.Security, "Privacy", "Review permissions and local data controls.")
            FeatureRow(Icons.Rounded.Settings, "About BACKCHANNEL", "P2P CONNECTED · Version 0.1.0")
        }
    }
}

@Composable
private fun ProfileScreen(localId: String) {
    Column(Modifier.fillMaxSize()) {
        BrandHeader("YOUR DEVICE IDENTITY")
        Column(Modifier.fillMaxWidth().padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(20.dp))
            Box(Modifier.size(92.dp).clip(RoundedCornerShape(30.dp)).background(PanelSoft), contentAlignment = Alignment.Center) {
                Icon(Icons.Rounded.Shield, null, tint = Lavender, modifier = Modifier.size(48.dp))
            }
            Spacer(Modifier.height(18.dp))
            Text("Your identity", color = MainText, fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
            Text("This preview ID is generated for this app session.", color = Muted, fontSize = 12.sp)
            Spacer(Modifier.height(24.dp))
            Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp)).background(Panel).padding(18.dp)) {
                Column {
                    Text("BACKCHANNEL ID", color = Muted, fontSize = 10.sp, letterSpacing = 1.3.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(localId, color = Lavender, fontSize = 20.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    Spacer(Modifier.height(10.dp))
                    Text("Persistent cryptographic identity and QR sharing are not implemented in this milestone.", color = Muted, fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
private fun FeatureRow(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, description: String) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 8.dp).clip(RoundedCornerShape(16.dp))
            .background(Panel).padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(40.dp).clip(RoundedCornerShape(13.dp)).background(Indigo.copy(alpha = .15f)), contentAlignment = Alignment.Center) {
            Icon(icon, null, tint = Lavender)
        }
        Spacer(Modifier.width(13.dp))
        Column(Modifier.weight(1f)) {
            Text(title, color = MainText, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Text(description, color = Muted, fontSize = 11.sp)
        }
        Icon(Icons.Rounded.ChevronRight, null, tint = Muted)
    }
}

@Composable
private fun BottomBar(page: Page, onSelect: (Page) -> Unit) {
    Row(
        Modifier.fillMaxWidth().background(Panel).padding(horizontal = 8.dp, vertical = 9.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavItem("Chats", Icons.Rounded.ChatBubbleOutline, page == Page.HOME) { onSelect(Page.HOME) }
        NavItem("People", Icons.Rounded.Contacts, page == Page.CONNECTIONS) { onSelect(Page.CONNECTIONS) }
        NavItem("Security", Icons.Rounded.Shield, page == Page.SECURITY) { onSelect(Page.SECURITY) }
        NavItem("Profile", Icons.Rounded.QrCode2, page == Page.PROFILE) { onSelect(Page.PROFILE) }
        NavItem("Settings", Icons.Rounded.Settings, page == Page.SETTINGS) { onSelect(Page.SETTINGS) }
    }
}

@Composable
private fun NavItem(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, selected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clip(RoundedCornerShape(12.dp)).clickable { onClick() }.padding(horizontal = 9.dp, vertical = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = label, tint = if (selected) Lavender else Muted, modifier = Modifier.size(20.dp))
        Spacer(Modifier.height(3.dp))
        Text(label, color = if (selected) Lavender else Muted, fontSize = 9.sp, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal)
    }
}
