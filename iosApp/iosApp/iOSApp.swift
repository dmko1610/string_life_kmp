import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        let driverFactory = DriverFactory()
        ServiceLocator.shared.doInit(driverFactory: driverFactory)
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
