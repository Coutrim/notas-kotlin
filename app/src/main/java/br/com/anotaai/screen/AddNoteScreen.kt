package br.com.anotaai.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.anotaai.R
import br.com.anotaai.model.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(noteViewModel: NoteViewModel, navController: NavHostController) {
    var titulo by remember { mutableStateOf("") }
    var conteudo by remember { mutableStateOf("") }
    var tituloError by remember { mutableStateOf(false) }
    var conteudoError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top, // Alinha o conteúdo na parte superior
        horizontalAlignment = Alignment.CenterHorizontally // Centraliza o conteúdo horizontalmente
    ) {
        TopAppBar(
            title = { Text("Adicione uma nota") },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            modifier = Modifier.padding(bottom = 16.dp) // Adiciona espaçamento abaixo da barra
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp) // Ajuste a altura conforme necessário
        ) {
            Image(
                painter = painterResource(id = R.drawable.gatoemcimadolivro), // Substitua pelo ID da sua imagem
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp) // Ajuste a altura conforme necessário
                    .padding(top = 16.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )
            // Créditos posicionados na parte inferior direita
            Text(
                text = "Image by Freepik",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    // Ajuste o padding conforme necessário
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = {
                if (it.length <= 60) titulo = it // Limita o título a 60 caracteres
            },
            label = { Text("Título") },
            isError = tituloError,
            placeholder = { Text("Digite o título da nota") },
            modifier = Modifier.fillMaxWidth() // Garante que o campo de texto ocupe toda a largura disponível
        )
        if (tituloError) {
            Text("O título da nota é obrigatório e pode ter até 60 carateres.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = conteudo,
            onValueChange = {
                if (it.length <= 255) conteudo = it
            },
            label = { Text("Conteúdo") },
            isError = conteudoError,
            placeholder = { Text("Digite o conteúdo da nota") },
            modifier = Modifier.fillMaxWidth() // Garante que o campo de texto ocupe toda a largura disponível
        )
        if (conteudoError) {
            Text("O conteúdo da nota é obrigatório e pode ter até 255 caracteres.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                tituloError = titulo.isBlank()
                conteudoError = conteudo.isBlank()

                if (!tituloError && !conteudoError) {
                    noteViewModel.saveNote(titulo, conteudo)
                    navController.navigate("list_notes") // Navegar para a lista de notas
                }
            },
            modifier = Modifier.fillMaxWidth() // Garante que o botão ocupe toda a largura disponível
        ) {
            Text("Salvar nota")
        }
    }
}
