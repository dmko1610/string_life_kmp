import Shared
import SwiftUI

struct AddInstrumentView: View {
    @Environment(\.dismiss) private var dismiss
    @State private var name = ""
    @State private var selectedTypeIndex = 0
    @State private var replacementDate = Date()
    @State private var hasDate = false

    private let viewModel = ServiceLocator.shared.instrumentViewModel
    private let types: [InstrumentType] = [.electric, .acoustic, .bass]

    var body: some View {
        Form {
            Section(header: Text("Instrument")) {
                TextField("Name", text: $name)

                Picker("Type", selection: $selectedTypeIndex) {
                    ForEach(types.indices, id: \.self) { i in
                        Text(types[i].displayName).tag(i)
                    }
                }.pickerStyle(.segmented)

                Toggle("Has string change date", isOn: $hasDate)
                if hasDate {
                    DatePicker(
                        "Date",
                        selection: $replacementDate,
                        displayedComponents: .date
                    )
                }

            }
        }
        .navigationTitle("Add Instrument")
        .toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
                Button("Save") {
                    let date = hasDate ? toLocalDate(replacementDate) : nil
                    viewModel.addInstrument(
                        name: name,
                        type: types[selectedTypeIndex],
                        lastStringChangeDate: date
                    )
                    dismiss()
                }
            }
        }
    }

    private func toLocalDate(_ date: Date) -> Kotlinx_datetimeLocalDate {
        let c = Calendar.current.dateComponents(
            [.year, .month, .day],
            from: date
        )
        return Kotlinx_datetimeLocalDate(
            year: Int32(c.year!),
            month: Int32(c.month!),
            day: Int32(c.day!)
        )
    }
}

#Preview {
    AddInstrumentView()
}
