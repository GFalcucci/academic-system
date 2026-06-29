import org.example.academic.system.model.AcademicData
import org.example.academic.system.repository.PersistenceType
import org.example.academic.system.service.PersistenceService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PersistenceServiceTest {
    private var service: PersistenceService? = null

    @BeforeEach
    fun setUp() {
        service = PersistenceService()
    }

    @Test
    fun deveTrocarTipoDePersistencia() {
        service!!.setPersistenceType(PersistenceType.TXT)
        val report = service!!.generateConfigurationReport()
        Assertions.assertTrue(report.contains("TXT"))
    }

    @Test
    fun deveGerarRelatorioDeConfiguracao() {
        val report = service!!.generateConfigurationReport()
        Assertions.assertNotNull(report)
        Assertions.assertTrue(report.contains("Tipo Atual"))
    }

    @Test
    fun deveSalvarDadosComRepositorioAtual() {
        val data = AcademicData()
        // Não deve lançar exceção ao salvar dados vazios
        Assertions.assertDoesNotThrow(Executable { service!!.saveData(data) })
    }

    @Test
    fun deveCarregarDadosComSucesso() {
        // Ao instanciar, o JSON deve carregar sem erros (mesmo que arquivo não exista, retorna AcademicData novo)
        Assertions.assertDoesNotThrow(Executable {
            val loadedData = service!!.loadData()
            Assertions.assertNotNull(loadedData)
        })
    }
}
