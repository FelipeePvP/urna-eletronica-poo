package br.edu.urna.dados;

import br.edu.urna.modelo.Voto;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class FirestoreRepositorioVotos implements RepositorioVotos {
    private final Firestore db;

    private FirestoreRepositorioVotos(Firestore db) {
        this.db = db;
    }

    public static RepositorioVotos criarSeConfigurado() throws Exception {
        String caminho = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        if (caminho == null || caminho.isBlank()) {
            return null;
        }

        if (FirebaseApp.getApps().isEmpty()) {
            try (FileInputStream serviceAccount = new FileInputStream(caminho)) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
                FirebaseApp.initializeApp(options);
            }
        }
        return new FirestoreRepositorioVotos(FirestoreClient.getFirestore());
    }

    @Override
    public void salvar(Voto voto) throws Exception {
        Map<String, Object> dados = new HashMap<>();
        dados.put("cargo", voto.getCargo());
        dados.put("tipoCargo", voto.getTipoCargo());
        dados.put("numeroCandidato", voto.getNumeroCandidato());
        dados.put("nomeCandidato", voto.getNomeCandidato());
        dados.put("sessaoId", voto.getSessaoId());
        dados.put("dataHora", voto.getDataHora().toString());

        db.collection("votos_simulados")
                .document(voto.getId())
                .set(dados)
                .get();
    }
}
