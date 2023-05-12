//
//  MovieRowUI.swift
//  UpComingMoviesKMM
//
//  Created by Felipe Menezes on 24/01/22.
//  Copyright © 2023 FMMobile. All rights reserved.
//

import SwiftUI

@available(iOS 15.0, *)
struct MovieRowUI: View {
    @StateObject var rowModel: MovieRowUIViewModel
    var body: some View {
        HStack(alignment: .top,
               spacing: 12.0) {
            ImageLoaderView(url: rowModel.posterPath)
                .frame(height: 150)
            VStack(alignment: .leading, spacing: 4.0) {
                HStack(alignment: .top) {
                    Text(rowModel.title)
                        .font(.title2)
                        .lineLimit(2)
                }
                Text(rowModel.genreTitle)
                    .font(.caption)
                Text(rowModel.sinopses)
                    .font(.caption2)
                    .padding(.all, 4.0)
                    .lineLimit(4)
                Spacer()
                HStack {
                    Spacer()
                    Text(rowModel.releaseDate)
                        .font(.caption2)
                }
            }
        }.padding(8)
    }
}

@available(iOS 14.0, *)
struct MovieRowUI_Previews: PreviewProvider {
    static var previews: some View {
        Group {
//            MovieRowUI(rowModel: PreviewEnviroment.cellModel).environmentObject(EnviromentData())
//            MovieRowUI(rowModel: PreviewEnviroment.cellModel2).environmentObject(EnviromentData())
        }.previewLayout(.fixed(width: 345, height: 200))
    }
}
