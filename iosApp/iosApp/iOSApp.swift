import SwiftUI
import Shared

@main
struct iOSApp: App {

    init() {
        ModulesKt.doInitKoin(config: nil)
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}