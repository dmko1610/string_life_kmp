import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        let driverFactory = DriverFactory()
        ServiceLocator.shared.doInit(driverFactory: driverFactory)
        Task {
            try? await ServiceLocator.shared.syncManager.sync()
        }
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
