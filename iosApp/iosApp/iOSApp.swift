import SwiftUI
import Shared

@main
struct iOSApp: App {
    // KMM - Koin Call
    init() {
        HelperKt.doInitKoin()
        HelperKt.debugMode()
    }
    private let injection = InjectionHelper()
    private var useCase: GetMovieListUseCase {
        GetMovieListUseCase(genreRepository: InjectionHelper().genreRepository,
                            movieRepository: InjectionHelper().movieRepository)
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
