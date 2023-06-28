import SwiftUI
import shared

@main
struct iOSApp: App {
    // KMM - Koin Call
    init() {
        HelperKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
            if #available(iOS 16.0, *) {
                NavigationStack {
                    ListMoviesSwiftUIView()
                }
            } else {
                ListMoviesSwiftUIView()
            }
		}
	}
}
