#built using mc-build (https://github.com/mc-build/mc-build)

tag @s remove justsummoned
execute at @s run function effects:lightning/__generated__/block/640
execute at @s run function effects:lightning/draw_spark
kill @s