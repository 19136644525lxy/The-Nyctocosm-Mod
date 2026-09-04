#built using mc-build (https://github.com/mc-build/mc-build)

execute if entity @a[tag=m.hypertraceMatched,tag=a.checkMatch,limit=1,predicate=nyctocosm:is_sneaking] run function magic:hypertrace/destroy_all
execute if entity @a[tag=m.hypertraceMatched,tag=a.checkMatch,limit=1,predicate=!nyctocosm:is_sneaking] run function magic:hypertrace/destroy