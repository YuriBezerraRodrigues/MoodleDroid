package util;

public interface Constantes {

	String IP_SERVIDOR = "10.0.2.2";
	
	String PORTA_SERVIDOR = "8080";
	
	String URL_VERIFICA_USUARIO = "http://" + IP_SERVIDOR + ":"
			+ PORTA_SERVIDOR + "/IFMoodleDroidServices/rest/listener/login/";
	
	String URL_VERIFICA_NOTICIAS = "http://" + IP_SERVIDOR + ":"
			+ PORTA_SERVIDOR + "/IFMoodleDroidServices/rest/listener/retornaNoticias/";
	
	String URL_CURSOS = "http://" + IP_SERVIDOR + ":"
			+ PORTA_SERVIDOR + "/IFMoodleDroidServices/rest/listener/verificarCursos/";
	
	String URL_CONTATOS = "http://" + IP_SERVIDOR + ":"
			+ PORTA_SERVIDOR + "/IFMoodleDroidServices/rest/listener/meusContatos/";
	
	String URL_FORUNS = "http://" + IP_SERVIDOR + ":"
			+ PORTA_SERVIDOR + "/IFMoodleDroidServices/rest/listener/retornaForumsCurso/";
	
	String URL_FORUNS_SEMANAS = "http://" + IP_SERVIDOR + ":"
			+ PORTA_SERVIDOR + "/IFMoodleDroidServices/rest/listener/retornaForunsSemanas/";
	
	
	String URL_TOPICOS_FORUM = "http://" + IP_SERVIDOR + ":"
			+ PORTA_SERVIDOR + "/IFMoodleDroidServices/rest/listener/retornaTopicosForum/";
	
	String URL_EXCLUIR_CONTATO = "http://" + IP_SERVIDOR + ":"
			+ PORTA_SERVIDOR + "/IFMoodleDroidServices/rest/listener/excluirContato/";
	
	String URL_BLOQUEAR_DESBLOQUEAR_CONTATO = "http://" + IP_SERVIDOR + ":"
			+ PORTA_SERVIDOR + "/IFMoodleDroidServices/rest/listener/bloquearOUDesbloquearContato/";
	
	String URL_BUSCAR_PESSOA = "http://" + IP_SERVIDOR + ":"
			+ PORTA_SERVIDOR + "/IFMoodleDroidServices/rest/listener/consultaUsuariosNome/";
	
	String URL_ADD_CONTATO = "http://" + IP_SERVIDOR + ":"
			+ PORTA_SERVIDOR + "/IFMoodleDroidServices/rest/listener/acrescentarUmContato/";
	
	String URL_DISCUSSAO_TOPICOS = "http://" + IP_SERVIDOR + ":"
			+ PORTA_SERVIDOR + "/IFMoodleDroidServices/rest/listener/retornaMensagensTopico/";

	String URL_SEMANAS = "http://" + IP_SERVIDOR + ":"
			+ PORTA_SERVIDOR + "/IFMoodleDroidServices/rest/listener/verificarSemanas";
}
