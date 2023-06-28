//
//  ListUIViewModel.swift
//  UpComingMoviesKMM
//
//  Created by Felipe Menezes on 20/03/22.
//  Copyright © 2023 FMMobile. All rights reserved.
//

import Combine
import SwiftUI
import shared

class ListUIViewModel: ObservableObject {
    @Published var movies: [Movie] = [Movie]()
    @Published var title: String = "Upcoming Movies"

    private var maxPages: Int64 = 1
    private(set) var currentPage: Int = 1
    
    private let injection = InjectionHelper()
    private lazy var useCase: GetMovieListUseCase = {
        GetMovieListUseCase(genreRepository: self.injection.genreRepository,
                            movieRepository: self.injection.movieRepository)
    }()

    @Published
    var isSaved: Bool = false
    @Published
    var buttonTitle = ""

    @available(iOS 15.0.0, *)
    @MainActor
    func moviesList() async throws {
        movies += try await listMovies()
    }

    @available(iOS 15.0.0, *)
    private func listMovies() async throws -> [Movie] {
        typealias ApiContinuation = CheckedContinuation<[Movie], Error>
        return try await withCheckedThrowingContinuation { (continuation: ApiContinuation) in
            self.useCase.loadMovies(page: Int32(self.currentPage)) { moviesList, error in
                if let error = error {
                    debugPrint("ERROR: \(error.localizedDescription)")
                    continuation.resume(throwing: error)
                    return
                }
                self.maxPages = moviesList?.totalPages ?? 0
                if let results = moviesList?.results {
                    continuation.resume(returning: results)
                }
            }
        }
    }

    func forwardPage() {
        self.currentPage += 1
    }

    func resetPage() {
        self.movies.removeAll()
        self.currentPage = 1
    }

    func buttonTitle(_ movie: Movie) {
        let favoriteTitle = "Favorite this item"
        DispatchQueue.main.async {
            self.useCase.isSaved(movie: movie) { result, error in
                if let error = error {
                    debugPrint("ERROR: \(error.localizedDescription)")
                    return
                }
                self.buttonTitle = (result == true) ? "My Favorite" : favoriteTitle
                self.isSaved = result?.boolValue ?? false
            }
        }
    }

    func saveItem(_ movie: Movie) {
        self.useCase.saveMovie(movie: movie, completionHandler: { error in
            debugPrint("error \(error?.localizedDescription ?? "")")
            DispatchQueue.main.async {
                if error == nil { self.isSaved = true }
            }
        })
    }

    func removeItem(_ movie: Movie) {
        self.useCase.removeMovie(movie: movie, completionHandler: { error in
            debugPrint("error \(error?.localizedDescription ?? "")")
            DispatchQueue.main.async {
                if error == nil { self.isSaved = false }
            }
        })
    }
}
