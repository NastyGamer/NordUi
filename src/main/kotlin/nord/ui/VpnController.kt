package nord.ui

import java.io.BufferedReader
import java.io.InputStreamReader


class VpnController() {

    private fun execAndGet(cmd: String): String {
        val proc = Runtime.getRuntime().exec(cmd.split(" ").toTypedArray())
        val stdIn = BufferedReader(InputStreamReader(proc.inputStream))
        return stdIn.readText();
    }

    fun isInstalled(): Boolean {
        return execAndGet("which nordvpn").contains("/usr/bin/nordvpn")
    }

    fun fittingVersion(): Boolean {
        return execAndGet("nordvpn -v").contains("3.8.9")
    }

    fun isLoggedIn(): Boolean {
        return !execAndGet("nordvpn account").contains("not")
    }

    fun login() {
        execAndGet("gnome-terminal --wait --window -- nordvpn login")
    }

    fun logout() {
        execAndGet("nordvpn logout")
    }

    fun connect(country: String, city: String) {
        if(city == "Default") execAndGet("nordvpn connect $country")
        else execAndGet("nordvpn connect $country $city")
    }

    fun disconnect() {
        execAndGet("nordvpn disconnect")
    }

    fun isConnected(): Boolean {
        return !execAndGet("nordvpn status").contains("Disconnected")
    }

    //12
    fun getCountries(): List<String> {
        val out = execAndGet("nordvpn countries")
        return out.drop(12)
            .replace(",", "")
            .split(" ").map { s -> s.sanitize() }
    }

    fun getCities(country: String): List<String> {
        val out = execAndGet("nordvpn cities $country")
        if (out.contains("not available")) return listOf("Default")
        return out.drop(12)
            .replace(",", "")
            .split(" ")
            .map { s -> s.sanitize() }
    }

    fun getInfo(): String {
        if (!isConnected()) return "Disconnected"
        return execAndGet("nordvpn status").split("\n")[1].split(": ")[1]
    }
}