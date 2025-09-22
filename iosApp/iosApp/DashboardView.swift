import SwiftUI

struct DashboardView: View {
    var body: some View {
        List {
            Text("No instruments yet")
        }
        .navigationTitle("Dashboard")
        .toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
                NavigationLink(destination: AddInstrumentView()) {
                    Image(systemName: "plus")
                }
            }
        }
    }
}
