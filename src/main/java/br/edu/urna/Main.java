package br.edu.urna;

import br.edu.urna.dados.FirestoreRepositorioVotos;
import br.edu.urna.dados.RepositorioVotos;
import br.edu.urna.dados.RepositorioVotosMemoria;
import br.edu.urna.modelo.Candidato;
import br.edu.urna.modelo.Cargo;
import br.edu.urna.modelo.CargoMajoritario;
import br.edu.urna.modelo.CargoProporcional;
import br.edu.urna.servico.ServicoVotacao;
import br.edu.urna.ui.TelaUrna;

import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        RepositorioVotos repositorio;
        try {
            repositorio = FirestoreRepositorioVotos.criarSeConfigurado();
            if (repositorio == null) {
                repositorio = new RepositorioVotosMemoria();
                System.out.println("Firebase não configurado: usando memória temporária.");
            } else {
                System.out.println("Conectado ao Firebase Cloud Firestore.");
            }
        } catch (Exception e) {
            System.err.println("Não foi possível iniciar o Firebase: " + e.getMessage());
            System.err.println("O programa continuará em modo de memória.");
            repositorio = new RepositorioVotosMemoria();
        }

        List<Candidato> candidatosProporcionais = List.of(
                new Candidato("101", "Ana Lima", "Partido das Árvores"),
                new Candidato("102", "Bruno Costa", "Partido das Águas"),
                new Candidato("103", "Carla Souza", "Partido dos Ventos")
        );
        List<Candidato> candidatosMajoritarios = List.of(
                new Candidato("20", "Marcos Rocha", "Partido do Futuro"),
                new Candidato("21", "Eva Martins", "Partido da Comunidade"),
                new Candidato("22", "Caio Nunes", "Partido da Cultura")
        );

        List<Cargo> cargos = List.of(
                new CargoProporcional("Representante proporcional", candidatosProporcionais),
                new CargoMajoritario("Representante majoritário", candidatosMajoritarios)
        );

        ServicoVotacao servico = new ServicoVotacao(cargos, repositorio);
        SwingUtilities.invokeLater(() -> {
            try {
                new TelaUrna(servico).setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Não foi possível abrir a aplicação: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
