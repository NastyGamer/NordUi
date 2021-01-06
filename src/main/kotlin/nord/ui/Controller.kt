package nord.ui

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ListView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Controller {

    @FXML
    private var countryListView: ListView<String>? = null

    @FXML
    private var infoLabel: Label? = null

    @FXML
    private var connectButton: Button? = null

    @FXML
    private var disconnectButton: Button? = null

    @FXML
    private var logoutButton: Button? = null

    @FXML
    private var loginButton: Button? = null

    @FXML
    private var serverListView: ListView<String>? = null

    private val controller = VpnController()

    @FXML
    fun initialize() {
        countryListView!!.items.addAll(controller.getCountries())
        countryListView!!.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            kotlin.run {
                serverListView!!.items.clear()
                serverListView!!.items.addAll(controller.getCities(newValue))
            }
        }
        connectButton!!.setOnMouseClicked {
            controller.connect(
                countryListView!!.selectionModel.selectedItem,
                serverListView!!.selectionModel.selectedItem
            )
        }
        disconnectButton!!.setOnMouseClicked { controller.disconnect() }
        loginButton!!.setOnMouseClicked { controller.login() }
        logoutButton!!.setOnMouseClicked { controller.logout() }
        GlobalScope.launch {
            while (true) {
                Platform.runLater { infoLabel!!.text = controller.getInfo() }
                if (controller.isLoggedIn()) {
                    Platform.runLater {
                        run {
                            loginButton!!.isDisable = true
                        }
                    }
                } else {
                    Platform.runLater {
                        run {
                            connectButton!!.isDisable = true
                            disconnectButton!!.isDisable = true
                            logoutButton!!.isDisable = true
                            loginButton!!.isDisable = false
                        }
                    }
                }
                if (controller.isConnected()) {
                    Platform.runLater {
                        run {
                            connectButton!!.isDisable = true
                        }
                    }
                } else {
                    Platform.runLater {
                        run {
                            connectButton!!.isDisable = false
                        }
                    }
                }
                delay(5000)
            }
        }
    }
}