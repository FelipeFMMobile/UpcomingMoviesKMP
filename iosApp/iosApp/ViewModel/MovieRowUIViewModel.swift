//
//  MovieRowUIViewModel.swift
//  UpComingMovies
//
//  Created by Felipe Menezes on 20/03/22.
//  Copyright © 2022 FMMobile. All rights reserved.
//

import Combine
import shared

class MovieRowUIViewModel: ObservableObject {
    var title: String = ""
    var posterPath: URL = URL(string: "https://www.apple.com")!
    var releaseDate: String = ""
    var genreTitle: String = ""
    var sinopses: String = ""
    var movieID: Int64 = 0
    @Published var isFavorite: Bool = false

    required init(movie: Movie) {
        title = movie.title
        let url = movie.posterPath ?? ""
        if let url = URL(string: url) {
            posterPath = url
        }
        sinopses = movie.overview
        let formatter = DateFormatter()
        let release = movie.releaseDate
        formatter.dateFormat = "yyyy-MM-dd"
        guard let date = formatter.date(from: release)  else { return }
        formatter.dateFormat = "d MMM"
        let sDate = formatter.string(from: date)
        releaseDate = "Comming on " + sDate
        movieID = movie.id
    }
}
