#built using mc-build (https://github.com/mc-build/mc-build)

execute if entity @a[tag=m.hypertraceMatched,limit=1,predicate=nyctocosm:is_sneaking] run function magic:hypertrace/pickup_all
execute if entity @a[tag=m.hypertraceMatched,limit=1,predicate=!nyctocosm:is_sneaking] run function magic:hypertrace/pickup