//
//  ListMoviesUI.swift
//  UpComingMoviesKMM
//
//  Created by Felipe Menezes on 24/01/22.
//  Copyright © 2023 FMMobile. All rights reserved.
//

import SwiftUI
import Shared

@available(iOS 15.0, *)
struct ListMoviesSwiftUIView: View {
    @StateObject var viewModel = ListUIViewModel()
    @State private var isLast = false
    @State var showAlert = false
    var body: some View {
        VStack {
            List(viewModel.movies, id: \.id) { movie in
                NavigationLink {
                    DetailSwiftUIView(movie: movie)
                        .environmentObject(viewModel)
                } label: {
                    let rowModel = MovieRowUIViewModel(movie: movie)
                    MovieRowSwiftUI(rowModel: rowModel)
                } .task {
                    isLast = viewModel.movies.last == movie
                    if isLast {
                        try? await loadMore()
                    }
                }
            }.listStyle(.plain)
            if isLast {
                ProgressView()
            }
        }.navigationTitle(viewModel.title)
        .toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
                Button(action: {
                    viewModel.getLocation()
                    let cancellable = viewModel.$info.sink { info in
                        showAlert = true
                    }
                }) {
                    Image(systemName: "location.fill")
                }.alert(isPresented: $showAlert) {
                    Alert(title: Text("User location"),
                          message: Text("Near of \(viewModel.info)"),
                          dismissButton: .default(Text("Ok"), action: {
                        showAlert = false
                    }))
                }
            }
        }
        .task {
            try? await loadMovies()
        }
        .refreshable {
            try? await refresh()
        }
    }
}

extension ListMoviesSwiftUIView {
    func titleForView() -> String? {
        return viewModel.title
    }

    private func loadMovies() async throws {
        try await viewModel.moviesList()
    }

    private func refresh() async throws {
        viewModel.resetPage()
        try await loadMovies()
    }

    // MARK: Scrolling Load
    private func loadMore() async throws {
         viewModel.forwardPage()
         try await loadMovies()
    }
}

struct ListMoviesUIView_Previews: PreviewProvider {
    static var previews: some View {
        ListMoviesSwiftUIView(viewModel: ListUIViewModel())
    }
}
