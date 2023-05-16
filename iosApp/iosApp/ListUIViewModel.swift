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

    @available(iOS 15.0.0, *)
    @MainActor
    func moviesList() async throws {
        movies += try await listMovies()
    }

    @available(iOS 15.0.0, *)
    private func listMovies() async throws -> [Movie] {
        typealias ApiContinuation = CheckedContinuation<[Movie], Error>
        return try await withCheckedThrowingContinuation { (continuation: ApiContinuation) in
            DispatchQueue.main.async { [weak self] in
                guard let self = self else { return }

                GetMovieListUseCase().loadMovies(page: Int32(self.currentPage)) { moviesList, error in

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
    }

    func forwardPage() {
        self.currentPage += 1
    }

    func resetPage() {
        self.movies.removeAll()
        self.currentPage = 1
    }
}
