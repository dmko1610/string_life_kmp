import Shared
import SwiftUI

@MainActor
class InstrumentState: ObservableObject {
    @Published var uiState: UiState? = nil
    private let viewModel = ServiceLocator.shared.instrumentViewModel

    init() {
        Task {
            for await state in viewModel.uiState {
                uiState = state
            }
        }
    }

    func delete(id: String) {
        viewModel.deleteInstrument(id: id)
    }
}

private struct InstrumentItem: Identifiable {
    let id: String
    let name: String
    let type: String
    let lastChangeDate: String?

    init(_ e: InstrumentEntity) {
        id = e.id
        name = e.name
        type = e.type.displayName
        lastChangeDate = e.lastStringChangeDate?.description()
    }
}

struct DashboardView: View {
    @StateObject private var state = InstrumentState()

    var body: some View {
        Group {
            switch onEnum(of: state.uiState) {
            case .none, .loading:
                ProgressView()
            case .error(let e):
                Text(e.message)
            case .success(let s):
                let items = (s.instruments as! [InstrumentEntity]).map(
                    InstrumentItem.init
                )
                List(items) { item in
                    VStack(alignment: .leading, spacing: 4) {
                        Text(item.name).font(.headline)
                        Text(item.type)
                            .font(.subheadline)
                            .foregroundStyle(.secondary)

                        if let date = item.lastChangeDate {
                            Text("Last change: \(date)")
                                .font(.caption)
                                .foregroundStyle(.secondary)
                        }
                    }
                    .swipeActions {
                        Button(role: .destructive) {
                            state.delete(id: item.id)
                        } label: {
                            Label("Delete", systemImage: "trash")
                        }
                    }
                }

            }
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
