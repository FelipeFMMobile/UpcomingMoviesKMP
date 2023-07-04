//
//  DetailSwiftUIView.swift
//  iosApp
//
//  Created by Felipe Menezes on 28/06/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct DetailSwiftUIView: View {
    @EnvironmentObject var viewModel: ListUIViewModel
    var movie: Movie
    var body: some View {
        VStack(alignment: .center, spacing: 8.0) {
            if let url = URL(string: movie.posterPath ?? "") {
                ImageLoaderView(url: url)
                                .frame(width: 200,
                                       height: 250)
                                .padding(16.0)
            }
            Text(movie.title)
             .font(.title)
             .padding(16.0)
            Text(movie.overview)
             .padding(16.0)
            Button {
                if viewModel.isSaved {
                    viewModel.removeItem(movie)
                    
                } else {
                    viewModel.saveItem(movie)
                }
            } label: {
                Text(viewModel.buttonTitle)
                    .padding(8)
            }.foregroundColor(viewModel.isSaved ? .cyan : .gray)
                .buttonStyle(.bordered)
            Spacer()
        }.onAppear {
            Task {
                try? await viewModel.buttonTitle(movie)
            }
        }
    }
}

struct DetailSwiftUIView_Previews: PreviewProvider {
    static var previews: some View {
        DetailSwiftUIView(movie: Movie(adult: false,
                                       backdropPath: nil,
                                       genreIDS: [],
                                       id: 1000,
                                       originalLanguage: "",
                                       originalTitle: "",
                                       overview:
                                        """
In an American desert town circa 1955, the itinerary of a Junior Stargazer/Space Cadet convention is spectacularly disrupted by world-changing events.
""",
                                       popularity: 1,
                                       posterPath: "/qdq40gRS8xKnpFt5V75t6lUHgpx.jpg",
                                       releaseDate: "2023-06-09",
                                       title: "Asteroid City",
                                       video: false,
                                       voteAverage: 1,
                                       voteCount: 10))
    }
}
