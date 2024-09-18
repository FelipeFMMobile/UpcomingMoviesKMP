//
//  ListUIViewModel.swift
//  UpComingMoviesKMM
//
//  Created by Felipe Menezes on 20/03/22.
//  Copyright © 2023 FMMobile. All rights reserved.
//

import Combine
import SwiftUI
import Shared

class ListUIViewModel: ObservableObject {
    @Published var movies: [Movie] = [Movie]()
    @Published var title: String = "Upcoming Movies"
    @Published var info: LocationInfo = LocationInfo(lat: 0.0, long: 0.0)
    
    let favoriteTitle = "Favorite this item"
    let myFavorite = "My Favorite"
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

    @MainActor
    func moviesList() async throws {
        movies += try await listMovies()
    }

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

    @MainActor
    func buttonTitle(_ movie: Movie) async throws -> Bool {
        typealias Continuation = CheckedContinuation<Bool, Error>
        return try await withCheckedThrowingContinuation { (continuation: Continuation) in
            self.useCase.isSaved(movie: movie) { result, error in
                if let error = error {
                    debugPrint("ERROR: \(error.localizedDescription)")
                    continuation.resume(returning: false)
                }
                self.buttonTitle = (result == true) ? self.myFavorite : self.favoriteTitle
                self.isSaved = result?.boolValue ?? false
                continuation.resume(returning: self.isSaved)
            }
        }
    }

    @MainActor
    func saveItem(_ movie: Movie) {
        self.useCase.saveMovie(movie: movie, completionHandler: { error in
            debugPrint("error \(error?.localizedDescription ?? "")")
            if error == nil {
                DispatchQueue.main.async {
                    self.isSaved = true
                    self.buttonTitle = self.myFavorite
                }
            }
        })
    }

    func removeItem(_ movie: Movie) {
        self.useCase.removeMovie(movie: movie, completionHandler: { error in
            debugPrint("error \(error?.localizedDescription ?? "")")
            if error == nil {
                DispatchQueue.main.async {
                    self.isSaved = false
                    self.buttonTitle = self.favoriteTitle
                }
            }
        })
    }

    func getLocation() {
        self.useCase.getLocation(callback: self)
    }
    
}

extension ListUIViewModel: LocationCallback {
    func onLocationError(error: KotlinThrowable) {
        debugPrint("error \(error.description())")
    }
    
    func onLocationReceived(locationInfo: LocationInfo) {
        info = locationInfo
    }
    
    
}
