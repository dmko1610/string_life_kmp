import SwiftUI

enum InstrumentType: String, CaseIterable, Identifiable {
    case electric = "Electric"
    case acoustic = "Acoustic"
    case bass = "Bass"
    var id: String { rawValue }
}

struct AddInstrumentView: View {
    @Environment(\.dismiss) private var dismiss
    @State private var name = ""
    @State private var type: InstrumentType = .electric
    @State private var replacementDate = Date()

    var body: some View {
        Form {
            Section(header: Text("Instrument")) {
                TextField("Name", text: $name)

                Picker("Type", selection: $type) {
                    ForEach(InstrumentType.allCases) { t in
                        Text(t.rawValue).tag(t)
                    }
                }.pickerStyle(.segmented)

                DatePicker(
                    "Replacement date", selection: $replacementDate,
                    displayedComponents: .date)
            }
        }
        .navigationTitle("Add Instrument")
        .toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
                Button("Save") {
                    dismiss()
                }
            }
        }
    }
}

#Preview {
    AddInstrumentView()
}
