package gui;

import javax.swing.*;
import java.awt.*;
import models.*;
import services.IArtGallery;
import exceptions.*;
import java.util.Vector;

public class JanelaPrincipal extends JFrame {
    private final IArtGallery sistema;

    // Componentes Globais para consulta
    private JTextArea txtAreaSaida;
    private JTextField txtBuscaAutor;
    private JTextField txtBuscaExpo;

    public JanelaPrincipal(IArtGallery sistema) {
        this.sistema = sistema;

        setTitle("ArtGallery - Gestor de Obras Digitais");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();

        // Abas de gerenciamento
        abas.addTab("Cadastrar Obra", criarAbaCadastro());
        abas.addTab("Avaliar Obra", criarAbaAvaliacao());
        abas.addTab("Gerenciar Obra (Edit/Del)", criarAbaGerenciarObra());
        abas.addTab("Gerenciar Exposições", criarAbaGerenciarExposicoes());
        abas.addTab("Consultas e Relatórios", criarAbaConsultas());

        add(abas);
    }

    /**
     * ABA 1: Formulário de Cadastro de Obras
     */
    private JPanel criarAbaCadastro() {
        JPanel painel = new JPanel(new GridLayout(6, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtTitulo = new JTextField();
        JTextField txtAutor = new JTextField();

        String[] tipos = {"Pintura Digital", "Modelagem 3D", "Arte Generativa"};
        JComboBox<String> cbTipo = new JComboBox<>(tipos);

        JTextField txtAtributo1 = new JTextField();
        JTextField txtAtributo2 = new JTextField();

        JLabel lblAtributo1 = new JLabel("Resolução:");
        JLabel lblAtributo2 = new JLabel("Software Utilizado:");

        cbTipo.addActionListener(e -> {
            String selecionado = (String) cbTipo.getSelectedItem();
            if ("Pintura Digital".equals(selecionado)) {
                lblAtributo1.setText("Resolução:");
                lblAtributo2.setText("Software Utilizado:");
            } else if ("Modelagem 3D".equals(selecionado)) {
                lblAtributo1.setText("Nº de Polígonos:");
                lblAtributo2.setText("Engine:");
            } else {
                lblAtributo1.setText("Algoritmo:");
                lblAtributo2.setText("Seed (Número):");
            }
        });

        JButton btnSalvar = new JButton("Publicar Obra");
        btnSalvar.addActionListener(e -> {
            try {
                Obra novaObra;
                String t = txtTitulo.getText();
                String a = txtAutor.getText();
                String tipo = (String) cbTipo.getSelectedItem();

                if ("Pintura Digital".equals(tipo)) {
                    novaObra = new PinturaDigital(t, a, txtAtributo1.getText(), txtAtributo2.getText());
                } else if ("Modelagem 3D".equals(tipo)) {
                    int poligos = Integer.parseInt(txtAtributo1.getText());
                    novaObra = new Modelagem3D(t, a, poligos, txtAtributo2.getText());
                } else {
                    long seed = Long.parseLong(txtAtributo2.getText());
                    novaObra = new ArteGenerativa(t, a, txtAtributo1.getText(), seed);
                }

                sistema.publicarObra(novaObra);
                JOptionPane.showMessageDialog(this, "Obra publicada com sucesso!");

                txtTitulo.setText(""); txtAutor.setText(""); txtAtributo1.setText(""); txtAtributo2.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Campos numéricos inválidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (ObraJaCadastradaException | DadoInvalidoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Cadastro", JOptionPane.WARNING_MESSAGE);
            }
        });

        painel.add(new JLabel("Título da Obra:")); painel.add(txtTitulo);
        painel.add(new JLabel("Autor (Artista):")); painel.add(txtAutor);
        painel.add(new JLabel("Tipo de Arte:")); painel.add(cbTipo);
        painel.add(lblAtributo1); painel.add(txtAtributo1);
        painel.add(lblAtributo2); painel.add(txtAtributo2);
        painel.add(new JLabel("")); painel.add(btnSalvar);

        return painel;
    }

    /**
     * ABA 2: Formulário de Avaliação
     */
    private JPanel criarAbaAvaliacao() {
        JPanel painel = new JPanel(new GridLayout(5, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtTituloObra = new JTextField();
        JTextField txtUsuario = new JTextField();
        JTextField txtNota = new JTextField();
        JTextField txtComentario = new JTextField();
        JButton btnAvaliar = new JButton("Enviar Avaliação");

        btnAvaliar.addActionListener(e -> {
            try {
                String titulo = txtTituloObra.getText();
                String usuario = txtUsuario.getText();
                int nota = Integer.parseInt(txtNota.getText());
                String comentario = txtComentario.getText();

                Avaliacao av = new Avaliacao(usuario, nota, comentario);
                sistema.avaliarObra(titulo, av);

                JOptionPane.showMessageDialog(this, "Avaliação registrada!");
                txtTituloObra.setText(""); txtUsuario.setText(""); txtNota.setText(""); txtComentario.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "A nota deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (NotaInvalidaException | ObraNaoEncontradaException | DadoInvalidoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro na Avaliação", JOptionPane.ERROR_MESSAGE);
            }
        });

        painel.add(new JLabel("Título da Obra a Avaliar:")); painel.add(txtTituloObra);
        painel.add(new JLabel("Seu Usuário:")); painel.add(txtUsuario);
        painel.add(new JLabel("Nota (0 a 10):")); painel.add(txtNota);
        painel.add(new JLabel("Comentário:")); painel.add(txtComentario);
        painel.add(new JLabel("")); painel.add(btnAvaliar);

        return painel;
    }

    /**
     * ABA 3: Consultas, Listagens e Late Binding
     */
    private JPanel criarAbaConsultas() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelFiltros = new JPanel(new GridLayout(3, 3, 5, 5));

        JButton btnListarAtivas = new JButton("Listar Obras Ativas");
        JButton btnTopObras = new JButton("Ver Top Obras (Ranking)");

        txtBuscaAutor = new JTextField();
        JButton btnBuscarAutor = new JButton("Buscar por Autor");

        txtBuscaExpo = new JTextField();
        JButton btnBuscarExpo = new JButton("Ver Obras da Exposição");

        painelFiltros.add(btnListarAtivas);
        painelFiltros.add(btnTopObras);
        painelFiltros.add(new JLabel(""));

        painelFiltros.add(new JLabel("Nome do Autor:")); painelFiltros.add(txtBuscaAutor); painelFiltros.add(btnBuscarAutor);
        painelFiltros.add(new JLabel("Nome da Exposição:")); painelFiltros.add(txtBuscaExpo); painelFiltros.add(btnBuscarExpo);

        txtAreaSaida = new JTextArea();
        txtAreaSaida.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtAreaSaida);

        btnListarAtivas.addActionListener(e -> atualizarMural(sistema.listarObras()));
        btnTopObras.addActionListener(e -> atualizarMural(sistema.topObras()));
        btnBuscarAutor.addActionListener(e -> atualizarMural(sistema.buscarPorAutor(txtBuscaAutor.getText())));
        btnBuscarExpo.addActionListener(e -> atualizarMural(sistema.obrasExpostas(txtBuscaExpo.getText())));

        painelPrincipal.add(painelFiltros, BorderLayout.NORTH);
        painelPrincipal.add(scroll, BorderLayout.CENTER);

        return painelPrincipal;
    }

    /**
     * ABA 4: Atualizar dados e Desativar uma Obra (Modificada)
     */
    /**
     * ABA 4: Atualizar dados e Desativar uma Obra (Apenas Artista e Desativação)
     */
    private JPanel criarAbaGerenciarObra() {
        JPanel painel = new JPanel(new GridLayout(5, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtBuscaTitulo = new JTextField();
        JTextField txtNovoAutor = new JTextField();

        JButton btnCarregar = new JButton("Buscar Obra");
        JButton btnAtualizar = new JButton("Salvar Alterações de Artista");
        JButton btnDesativar = new JButton("Desativar Obra (Remover)");

        final Obra[] obraEditando = {null};

        btnCarregar.addActionListener(e -> {
            Vector<Obra> todas = sistema.listarObras();
            String tituloBusca = txtBuscaTitulo.getText();
            obraEditando[0] = null;

            for (Obra o : todas) {
                if (o.getTitulo().equalsIgnoreCase(tituloBusca)) {
                    obraEditando[0] = o;
                    break;
                }
            }

            if (obraEditando[0] != null) {
                txtNovoAutor.setText(obraEditando[0].getAutor());
                JOptionPane.showMessageDialog(this, "Obra carregada! Modifique o nome do artista abaixo.");
            } else {
                JOptionPane.showMessageDialog(this, "Obra ativa não encontrada com esse título.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAtualizar.addActionListener(e -> {
            if (obraEditando[0] == null) {
                JOptionPane.showMessageDialog(this, "Por favor, busque uma obra válida primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                // Altera apenas o atributo solicitado
                obraEditando[0].setAutor(txtNovoAutor.getText());

                // Envia para o service aplicar as validações e atualizar no repositório
                sistema.atualizarObra(obraEditando[0]);

                JOptionPane.showMessageDialog(this, "Nome do artista atualizado com sucesso!");
                txtBuscaTitulo.setText(""); txtNovoAutor.setText("");
                obraEditando[0] = null;
            } catch (ObraNaoEncontradaException | DadoInvalidoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Modificação", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ação de Desativar a Obra
        btnDesativar.addActionListener(e -> {
            try {
                String titulo = txtBuscaTitulo.getText();
                sistema.removerObra(titulo);
                JOptionPane.showMessageDialog(this, "A obra foi setada como desativada no acervo!");

                txtBuscaTitulo.setText(""); txtNovoAutor.setText("");
                obraEditando[0] = null;
            } catch (ObraNaoEncontradaException | DadoInvalidoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao Desativar", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Montagem do Grid
        painel.add(new JLabel("Título da Obra Alvo:")); painel.add(txtBuscaTitulo);
        painel.add(new JLabel("")); painel.add(btnCarregar);
        painel.add(new JLabel("Nome do Artista:")); painel.add(txtNovoAutor);
        painel.add(new JLabel("")); painel.add(new JLabel(""));
        painel.add(btnAtualizar); painel.add(btnDesativar);

        return painel;
    }

    /**
     * ABA 5: Criar Exposição e Vincular Obras a ela
     */
    private JPanel criarAbaGerenciarExposicoes() {
        JPanel painel = new JPanel(new GridLayout(6, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtNomeExpo = new JTextField();
        JButton btnCriarExpo = new JButton("Criar Nova Exposição");

        JTextField txtNomeExpoAlvo = new JTextField();
        JTextField txtTituloObraVincular = new JTextField();
        JButton btnVincular = new JButton("Adicionar Obra à Exposição");

        btnCriarExpo.addActionListener(e -> {
            try {
                String nome = txtNomeExpo.getText();
                Exposicao novaExpo = new Exposicao(nome);
                sistema.criarExposicao(novaExpo);

                JOptionPane.showMessageDialog(this, "Exposição '" + nome + "' criada com sucesso!");
                txtNomeExpo.setText("");
            } catch (DadoInvalidoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Criação", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVincular.addActionListener(e -> {
            String nomeExpo = txtNomeExpoAlvo.getText();
            String tituloObra = txtTituloObraVincular.getText();

            try {
                sistema.adicionarObraEmExposicao(nomeExpo, tituloObra);

                JOptionPane.showMessageDialog(this, "Obra adicionada à exposição com sucesso!");
                txtNomeExpoAlvo.setText(""); txtTituloObraVincular.setText("");
            } catch (ObraNaoEncontradaException | DadoInvalidoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Vínculo", JOptionPane.ERROR_MESSAGE);
            }
        });

        painel.add(new JLabel("Nome da Nova Exposição:")); painel.add(txtNomeExpo);
        painel.add(new JLabel("")); painel.add(btnCriarExpo);
        painel.add(new JLabel("--- Vincular Obra ---")); painel.add(new JLabel("----------------------"));
        painel.add(new JLabel("Nome da Exposição Alvo:")); painel.add(txtNomeExpoAlvo);
        painel.add(new JLabel("Título da Obra:")); painel.add(txtTituloObraVincular);
        painel.add(new JLabel("")); painel.add(btnVincular);

        return painel;
    }

    /**
     * Método auxiliar para renderizar a saída aplicando o LATE BINDING
     */
    private void atualizarMural(Vector<Obra> lista) {
        txtAreaSaida.setText("");
        if (lista == null || lista.isEmpty()) {
            txtAreaSaida.setText("Nenhum registro encontrado.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Obra obra : lista) {
            sb.append("==================================================\n");
            sb.append(obra.exibirDetalhes()).append("\n");
            sb.append(String.format("Média de Notas: %.1f / 10\n", obra.mediaAvaliacoes()));
            sb.append("Status: ").append(obra.isAtiva() ? "Disponível\n" : "Desativada\n");

            sb.append("\n>> COMENTÁRIOS E FEEDBACKS:\n");
            Vector<Avaliacao> listaAvaliacoes = obra.getAvaliacoes();

            if (listaAvaliacoes == null || listaAvaliacoes.isEmpty()) {
                sb.append("   (Nenhuma avaliação recebida para esta obra até o momento.)\n");
            } else {
                for (Avaliacao av : listaAvaliacoes) {
                    sb.append("   * [Usuário: ").append(av.getUsuario())
                            .append("] | Nota: ").append(av.getNota()).append("/10\n")
                            .append("     \"").append(av.getComentario()).append("\"\n\n");
                }
            }
        }
        sb.append("==================================================\n");
        txtAreaSaida.setText(sb.toString());
    }
}